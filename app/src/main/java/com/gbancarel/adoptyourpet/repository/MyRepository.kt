package com.gbancarel.adoptyourpet.repository

import android.util.Log
import com.gbancarel.adoptyourpet.interactor.PetFinder
import com.gbancarel.adoptyourpet.repository.error.CannotDecodeJsonException
import com.gbancarel.adoptyourpet.repository.parser.PetFinderParser
import com.gbancarel.adoptyourpet.repository.parser.TokenParser
import com.gbancarel.adoptyourpet.repository.error.ErrorStatusException
import com.gbancarel.adoptyourpet.repository.service.PetFinderService
import com.gbancarel.adoptyourpet.repository.service.TokenService
import javax.inject.Inject


class MyRepository @Inject constructor(
        var petFinderService: PetFinderService,
        var tokenService: TokenService,
        var petFinderParser: PetFinderParser,
        var tokenParser: TokenParser
) {

    @Throws(
        ErrorStatusException::class,
        CannotDecodeJsonException::class
    )
    fun getCall (): PetFinder {
        val token = tokenService.getToken("https://api.petfinder.com/v2/oauth2/token")
        var authToken = ""

        if (token.statusCode != 200 && token.statusCode != 201) {
            throw ErrorStatusException("http request fail for token")
        } else {
            val tokenEntityJSON = tokenParser.parse(token.body)
            authToken = tokenEntityJSON!!.access_token
        }

        val response = petFinderService.get("https://api.petfinder.com/v2/animals?type=dog&page=1", authToken)

        if (response.statusCode != 200 && response.statusCode != 201) {
            throw ErrorStatusException("http request fail")
        } else {
            val petFinderEntityJSON = petFinderParser.parse(response.body)

            if (petFinderEntityJSON != null) {
                return PetFinder(
                        type = petFinderEntityJSON.animals[0].type,
                        age = petFinderEntityJSON.animals[0].age,
                        gender = petFinderEntityJSON.animals[0].gender,
                        size = petFinderEntityJSON.animals[0].size,
                        name = petFinderEntityJSON.animals[0].name,
                        description = petFinderEntityJSON.animals[0].description
                )
            } else {
                throw CannotDecodeJsonException("adapter from json fail")
            }
        }
    }
}