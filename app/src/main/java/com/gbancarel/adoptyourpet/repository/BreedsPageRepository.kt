package com.gbancarel.adoptyourpet.repository

import android.util.Log
import com.gbancarel.adoptyourpet.Activity.SearchPageViewModel
import com.gbancarel.adoptyourpet.interactor.data.listBreeds.Breeds
import com.gbancarel.adoptyourpet.repository.dao.BreedDao
import com.gbancarel.adoptyourpet.repository.error.CannotDecodeJsonException
import com.gbancarel.adoptyourpet.repository.error.ErrorStatusException
import com.gbancarel.adoptyourpet.repository.error.NoInternetConnectionAvailable
import com.gbancarel.adoptyourpet.repository.json.listBreeds.ListBreedsJSON
import com.gbancarel.adoptyourpet.repository.local.BreedLocal
import com.gbancarel.adoptyourpet.repository.parser.BreedsParser
import com.gbancarel.adoptyourpet.repository.service.PetFinderService
import com.gbancarel.adoptyourpet.state.AnimalSelected
import javax.inject.Inject
import kotlin.jvm.Throws

class BreedsPageRepository @Inject constructor(
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
    fun getListBreeds(): List<Breeds> {
        try {
            val myViewmodel = SearchPageViewModel()
            if (myViewmodel.liveData.value == AnimalSelected.dog) {
                Log.i("mylog",myViewmodel.liveData.value.toString())
            } else {
                Log.i("mylog",myViewmodel.liveData.value.toString())
            }
            val response = service.get("$BASE_URL/types/dog/breeds")
            Log.i("mylog",response.body.toString())

            if (response.statusCode != 200 && response.statusCode != 201) {
                Log.i("mylog", "okhttp fail")
                throw ErrorStatusException("http request fail")
            } else {
                val breedsEntityJSON = parser.parse(response.body)
                if (breedsEntityJSON != null) {
                    val result = parseJson(breedsEntityJSON)
                    dao.deleteAll()
                    dao.insertAll(result.map { BreedLocal(primary = it.name) })
                    return result
                } else {
                    Log.i("mylog", "moshi fail")
                    throw CannotDecodeJsonException("adapter from json fail")
                }
            }
        } catch (e1: NoInternetConnectionAvailable) {
            throw NoInternetConnectionAvailable("No Internet")
        }
    }

    private fun parseJson(breedsEntityJSON: ListBreedsJSON) : List<Breeds> {
        Log.i("mylog", "je suis dans parseJson")
        val breeds: List<Breeds?> =
            breedsEntityJSON.breeds.map { BreedsJSON ->
                if (BreedsJSON != null) {
                    Breeds(name = BreedsJSON.name)
                } else {
                    null
                }
            }
        return breeds.filterNotNull()
    }
}