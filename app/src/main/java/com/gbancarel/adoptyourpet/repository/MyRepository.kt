package com.gbancarel.adoptyourpet.repository

import android.util.Log
import com.gbancarel.adoptyourpet.interactor.PetFinder
import javax.inject.Inject


class MyRepository @Inject constructor(
    var service: PetFinderService,
    var parser: PetFinderParser
) {

    @Throws(
        ErrorStatusException::class,
        CannotDecodeJsonException::class
    )
    fun getCall (): PetFinder {
        val response = service.get("https://api.chucknorris.io/jokes/random")

        if (response.statusCode != 200 && response.statusCode != 201) {
            throw ErrorStatusException("http request fail")
        }

        val petFinderEntityJSON = parser.parse(response.body)

        if (petFinderEntityJSON != null) {
            return PetFinder(petFinderEntityJSON.value)
        } else {
            throw CannotDecodeJsonException("adapter from json fail")
        }
    }
}