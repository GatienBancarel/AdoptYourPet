package com.gbancarel.adoptyourpet.repository

import android.util.Log
import com.gbancarel.adoptyourpet.interactor.data.Age
import com.gbancarel.adoptyourpet.interactor.data.Size
import com.gbancarel.adoptyourpet.interactor.data.listAnimal.*
import com.gbancarel.adoptyourpet.presenter.data.listGender.GenderViewModel
import com.gbancarel.adoptyourpet.repository.dao.AnimalDao
import com.gbancarel.adoptyourpet.repository.dao.AnimalResultDao
import com.gbancarel.adoptyourpet.repository.error.CannotDecodeJsonException
import com.gbancarel.adoptyourpet.repository.error.ErrorStatusException
import com.gbancarel.adoptyourpet.repository.error.NoInternetConnectionAvailable
import com.gbancarel.adoptyourpet.repository.json.listAnimal.PetFinderJSON
import com.gbancarel.adoptyourpet.repository.local.*
import com.gbancarel.adoptyourpet.repository.parser.PetFinderParser
import com.gbancarel.adoptyourpet.repository.service.PetFinderService
import com.gbancarel.adoptyourpet.state.AnimalSelected
import javax.inject.Inject
import kotlin.jvm.Throws

class ListPetResultRepository @Inject constructor(
    var petFinderService: PetFinderService,
    var petFinderParser: PetFinderParser,
    var dao: AnimalResultDao
) {

    private val BASE_URL = "https://api.petfinder.com/v2" // TODO GBA

    @Throws(
        ErrorStatusException::class,
        CannotDecodeJsonException::class,
        NoInternetConnectionAvailable::class
    )
    fun getListAnimal(
        animalSelected: AnimalSelected,
        breedsSelected: List<String>,
        sizeSelected: List<Size>,
        ageSelected: List<Age>,
        colorsSelected: List<String>,
        genderSelected: GenderViewModel?
    ): List<PetAnimal> {
        try {
            Log.i("PBA", "$BASE_URL/animals?type=${animalSelected}&breed=${breedsSelected.joinToString(",")}&age=${ageSelected.map { it.value }.joinToString(",")}&gender=${genderSelected}&size=${sizeSelected.map { it.value }.joinToString(",")}&color=${colorsSelected.joinToString(",")}&limit=100")
            val response = petFinderService.get(
                "$BASE_URL/animals?type=${animalSelected}&breed=${breedsSelected.joinToString(",")}&age=${ageSelected.map { it.value }.joinToString(",")}&gender=${genderSelected}&size=${sizeSelected.map { it.value }.joinToString(",")}&color=${colorsSelected.joinToString(",")}&limit=100"
            )

            if (response.statusCode != 200 && response.statusCode != 201) {
                throw ErrorStatusException("http request fail")
            } else {
                val petFinderEntityJSON = petFinderParser.parse(response.body)
                if (petFinderEntityJSON != null) {
                    val result = parseJson(petFinderEntityJSON)
                    dao.deleteAll()
                    dao.insertAll(result.map {
                        PetAnimalLocal(
                            type = it.type,
                            breed = it.breed,
                            color = it.color,
                            age = it.age,
                            gender = it.gender,
                            size = it.size,
                            environment = it.environment?.let { environment ->
                                EnvironmentLocal(
                                    environment.children,
                                    environment.children,
                                    environment.children,
                                )
                            },
                            name = it.name,
                            description = it.description,
                            photo = it.photos.firstOrNull()?.let { photo ->
                                PhotoLocal(
                                    photo.small,
                                    photo.medium,
                                    photo.large,
                                    photo.full,
                                )
                            },
                            contact = it.contact?.let { contact ->
                                ContactLocal(
                                    contact.email,
                                    contact.phone,
                                    contact.address?.let { address ->
                                        AddressLocal(
                                            address.address1,
                                            address.address2,
                                            address.city,
                                            address.state,
                                            address.postCode,
                                            address.country
                                        )
                                    }
                                )
                            }
                        )
                    })
                    return result
                } else {
                    throw CannotDecodeJsonException("adapter from json fail")
                }
            }
        } catch (e1: NoInternetConnectionAvailable) {
            throw NoInternetConnectionAvailable("No Internet")
        }
    }

    fun getLocalListAnimal() = dao.getAll().map {
        PetAnimal(
            type = it.type,
            breed = it.breed,
            color = it.color,
            age = it.age,
            gender = it.gender,
            size = it.size,
            environment = it.environment?.let { environmentLocal ->
                Environment(
                    environmentLocal.children,
                    environmentLocal.dog,
                    environmentLocal.cat
                )
            },
            name = it.name,
            description = it.description,
            photos = listOf(it.photo?.let { photoLocal ->
                Photo(
                    photoLocal.small,
                    photoLocal.medium,
                    photoLocal.large,
                    photoLocal.full
                )
            }).filterNotNull(),
            contact = it.contact?.let { contactLocal ->
                Contact(
                    contactLocal.email,
                    contactLocal.phone,
                    contactLocal.address?.let { addressLocal: AddressLocal ->
                        Address(
                            addressLocal.address1,
                            addressLocal.address2,
                            addressLocal.city,
                            addressLocal.state,
                            addressLocal.postCode,
                            addressLocal.country
                        )
                    },
                )
            }
        )
    }

    private fun parseJson(petFinderEntityJSON: PetFinderJSON): List<PetAnimal> {
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