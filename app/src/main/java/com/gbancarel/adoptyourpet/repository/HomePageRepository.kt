package com.gbancarel.adoptyourpet.repository

import com.gbancarel.adoptyourpet.interactor.data.listAnimal.*
import com.gbancarel.adoptyourpet.repository.error.CannotDecodeJsonException
import com.gbancarel.adoptyourpet.repository.parser.PetFinderParser
import com.gbancarel.adoptyourpet.repository.error.ErrorStatusException
import com.gbancarel.adoptyourpet.repository.error.NoInternetConnectionAvailable
import com.gbancarel.adoptyourpet.repository.json.listAnimal.PetFinderJSON
import com.gbancarel.adoptyourpet.repository.service.PetFinderService
import javax.inject.Inject
import kotlin.jvm.Throws


class HomePageRepository @Inject constructor(
    var petFinderService: PetFinderService,
    var petFinderParser: PetFinderParser
) {

    private val BASE_URL = "https://api.petfinder.com/v2" // TODO GBA

    @Throws(
        ErrorStatusException::class,
        CannotDecodeJsonException::class,
        NoInternetConnectionAvailable::class
    )
    fun getListAnimal(): List<PetAnimal> {
        try {
            val response = petFinderService.get("$BASE_URL/animals?type=dog&page=1")

            if (response.statusCode != 200 && response.statusCode != 201) {
                throw ErrorStatusException("http request fail")
            } else {
                val petFinderEntityJSON = petFinderParser.parse(response.body)
                if (petFinderEntityJSON != null) {
                    return parseJson(petFinderEntityJSON)
                } else {
                    throw CannotDecodeJsonException("adapter from json fail")
                }
            }
        } catch (e1: NoInternetConnectionAvailable) {
            throw NoInternetConnectionAvailable("No Internet")
        }
    }

    private fun parseJson(petFinderEntityJSON: PetFinderJSON) : List<PetAnimal> {
        val listPetAnimal: List<PetAnimal?> =
            petFinderEntityJSON.animals.map { PetAnimalJSON ->
                when {
                    PetAnimalJSON?.name == null -> null
                    PetAnimalJSON.type == null -> null
                    else -> {
                        val address = if (
                            PetAnimalJSON.contact?.address?.city != null
                            && PetAnimalJSON.contact.address.country != null
                            && PetAnimalJSON.contact.address.state != null
                        )
                            Address(
                                address1 = PetAnimalJSON.contact.address.address1,
                                address2 = PetAnimalJSON.contact.address.address2,
                                city = PetAnimalJSON.contact.address.city,
                                state = PetAnimalJSON.contact.address.state,
                                postCode = PetAnimalJSON.contact.address.postCode,
                                country = PetAnimalJSON.contact.address.country
                            ) else null

                        val environment = if (
                            listOfNotNull(
                                PetAnimalJSON.environment?.children,
                                PetAnimalJSON.environment?.dog,
                                PetAnimalJSON.environment?.cat
                            ).isNotEmpty()
                        ) {
                            Environment(
                                children = PetAnimalJSON.environment?.children ?: false,
                                dog = PetAnimalJSON.environment?.dog ?: false,
                                cat = PetAnimalJSON.environment?.cat ?: false
                            )
                        } else {
                            null
                        }

                        PetAnimal(
                            type = PetAnimalJSON.type,
                            breed = PetAnimalJSON.breeds?.primary,
                            color = PetAnimalJSON.colors?.primary,
                            age = PetAnimalJSON.age,
                            gender = PetAnimalJSON.gender,
                            size = PetAnimalJSON.size,
                            environment = environment,
                            name = PetAnimalJSON.name,
                            description = PetAnimalJSON.description,
                            photos = PetAnimalJSON.photos?.map { PhotoJSON ->
                                Photo(
                                    small = PhotoJSON.small,
                                    medium = PhotoJSON.medium,
                                    large = PhotoJSON.large,
                                    full = PhotoJSON.full
                                )
                            } ?: emptyList(),
                            contact = Contact(
                                email = PetAnimalJSON.contact?.email,
                                phone = PetAnimalJSON.contact?.phone,
                                address = address
                            )
                        )
                    }
                }
            }
        return listPetAnimal.filterNotNull()
    }
}