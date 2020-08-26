package com.gbancarel.adoptyourpet.repository

import android.util.Log
import com.gbancarel.adoptyourpet.interactor.data.listBreeds.BreedLocalInteractor
import com.gbancarel.adoptyourpet.interactor.data.listBreeds.Breeds
import com.gbancarel.adoptyourpet.repository.dao.BreedDao
import com.gbancarel.adoptyourpet.repository.error.CannotDecodeJsonException
import com.gbancarel.adoptyourpet.repository.error.ErrorStatusException
import com.gbancarel.adoptyourpet.repository.error.NoInternetConnectionAvailable
import com.gbancarel.adoptyourpet.repository.json.listBreeds.ListBreedsJSON
import com.gbancarel.adoptyourpet.repository.local.BreedLocal
import com.gbancarel.adoptyourpet.repository.parser.BreedsParser
import com.gbancarel.adoptyourpet.repository.service.PetFinderService
import javax.inject.Inject
import kotlin.jvm.Throws

class ListBreedsRepository @Inject constructor(
    var service: PetFinderService,
    var parser: BreedsParser,
    var dao: BreedDao
) {

    private val BASE_URL = "https://api.petfinder.com/v2" // TODO GBA

    @Throws(
        ErrorStatusException::class,
        CannotDecodeJsonException::class,
        NoInternetConnectionAvailable::class
    )
    fun getListBreeds(animalSelected: String) {
        try {
            val response = service.get("$BASE_URL/types/$animalSelected/breeds")

            if (response.statusCode != 200 && response.statusCode != 201) {
                throw ErrorStatusException("http request fail")
            } else {
                val breedsEntityJSON = parser.parse(response.body)
                if (breedsEntityJSON != null) {
                    val result = parseJson(breedsEntityJSON)
                    dao.deleteAll()
                    dao.insertAll(result.map { BreedLocal(primary = it.name) })
                } else {
                    throw CannotDecodeJsonException("adapter from json fail")
                }
            }
        } catch (e1: NoInternetConnectionAvailable) {
            throw NoInternetConnectionAvailable("No Internet")
        }
    }

    fun getListBreedsLocal() : List<BreedLocalInteractor> {
        return dao.getAll().map { BreedLocal->
            BreedLocalInteractor(
                name = BreedLocal.primary
            )
        }
    }

    private fun parseJson(breedsEntityJSON: ListBreedsJSON) : List<Breeds> {
        return breedsEntityJSON.breeds.map { BreedsJSON ->
                Breeds(name = BreedsJSON!!.name)
            }
    }
}