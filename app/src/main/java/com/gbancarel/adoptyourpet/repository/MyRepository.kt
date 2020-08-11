package com.gbancarel.adoptyourpet.repository

import android.util.Log
import com.gbancarel.adoptyourpet.interactor.PetAnimal
import com.gbancarel.adoptyourpet.interactor.PetFinder
import com.gbancarel.adoptyourpet.repository.error.CannotDecodeJsonException
import com.gbancarel.adoptyourpet.repository.parser.PetFinderParser
import com.gbancarel.adoptyourpet.repository.parser.TokenParser
import com.gbancarel.adoptyourpet.repository.error.ErrorStatusException
import com.gbancarel.adoptyourpet.repository.service.MyInterceptor
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
    fun getToken() :String?{
        return ""
    }

    @Throws(
        ErrorStatusException::class,
        CannotDecodeJsonException::class
    )
    fun getCall (): PetFinder {
        val interceptor = MyInterceptor("old token")
        val authToken = interceptor.newToken()
        val response = petFinderService.get("https://api.petfinder.com/v2/animals?type=dog&page=1", authToken)
        //Log.i("mylog", response.body.toString())

        if (response.statusCode != 200 && response.statusCode != 201) {
            throw ErrorStatusException("http request fail")
        } else {
            val petFinderEntityJSON = petFinderParser.parse(response.body)
            //Log.i("mylog", petFinderEntityJSON?.animals?.get(0)?.name.toString())

            if (petFinderEntityJSON != null) {
                return PetFinder(petFinderEntityJSON.animals.map { PetAnimalJSON ->
                    PetAnimal(
                        type = PetAnimalJSON.type,
                        age = PetAnimalJSON.age,
                        gender = PetAnimalJSON.gender,
                        size = PetAnimalJSON.size,
                        name = PetAnimalJSON.name,
                        description = PetAnimalJSON.description
                    )
                })
            } else {
                throw CannotDecodeJsonException("adapter from json fail")
            }
        }
    }
}