package com.gbancarel.adoptyourpet.repository

import com.gbancarel.adoptyourpet.interactor.data.listColors.Colors
import com.gbancarel.adoptyourpet.repository.dao.ColorsDao
import com.gbancarel.adoptyourpet.repository.error.CannotDecodeJsonException
import com.gbancarel.adoptyourpet.repository.error.ErrorStatusException
import com.gbancarel.adoptyourpet.repository.error.NoInternetConnectionAvailable
import com.gbancarel.adoptyourpet.repository.json.listColors.TypeJSON
import com.gbancarel.adoptyourpet.repository.local.ColorsLocal
import com.gbancarel.adoptyourpet.repository.parser.ColorsParser
import com.gbancarel.adoptyourpet.repository.service.PetFinderService
import com.gbancarel.adoptyourpet.state.AnimalSelected
import javax.inject.Inject
import kotlin.jvm.Throws

class ListColorsRepository @Inject constructor(
    var service: PetFinderService,
    var parser: ColorsParser,
    var dao: ColorsDao
) {

    private val BASE_URL = "https://api.petfinder.com/v2" // TODO GBA

    @Throws(
        ErrorStatusException::class,
        CannotDecodeJsonException::class,
        NoInternetConnectionAvailable::class
    )
    fun loadColors(animalSelected: AnimalSelected): List<Colors> {
        try {
            val response = service.get("$BASE_URL/types/$animalSelected")

            if (response.statusCode != 200 && response.statusCode != 201) {
                throw ErrorStatusException("http request fail")
            } else {
                val colorsEntityJSON = parser.parse(response.body)
                if (colorsEntityJSON != null) {
                    val result = parseJson(colorsEntityJSON)
                    dao.deleteAll()
                    dao.insertAll(result.map { ColorsLocal(primary = it.primary) })
                    return result
                } else {
                    throw CannotDecodeJsonException("adapter from json fail")
                }
            }
        } catch (e1: NoInternetConnectionAvailable) {
            throw NoInternetConnectionAvailable("No Internet")
        }
    }

    private fun parseJson(colorsEntityJSON: TypeJSON) : List<Colors> {
        return colorsEntityJSON.type.colors.map {
            Colors(primary = it)
        }
    }
}