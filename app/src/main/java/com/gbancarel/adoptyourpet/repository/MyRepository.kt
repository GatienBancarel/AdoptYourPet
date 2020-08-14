package com.gbancarel.adoptyourpet.repository

import com.gbancarel.adoptyourpet.interactor.donnes.*
import com.gbancarel.adoptyourpet.repository.error.CannotDecodeJsonException
import com.gbancarel.adoptyourpet.repository.parser.PetFinderParser
import com.gbancarel.adoptyourpet.repository.parser.TokenParser
import com.gbancarel.adoptyourpet.repository.error.ErrorStatusException
import com.gbancarel.adoptyourpet.repository.json.PhotoJSON
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

        if (response.statusCode != 200 && response.statusCode != 201) {
            throw ErrorStatusException("http request fail")
        } else {
            val petFinderEntityJSON = petFinderParser.parse(response.body)
            if (petFinderEntityJSON != null) {
                val listPetAnimal: List<PetAnimal?> = petFinderEntityJSON.animals.map { PetAnimalJSON ->
                    if (PetAnimalJSON?.name == null) {
                        null
                    } else {
                        PetAnimal(
                                type = PetAnimalJSON.type,
                                breeds = Breed(primary = PetAnimalJSON.breeds?.primary),
                                colors = Color(primary = PetAnimalJSON.colors?.primary),
                                age = PetAnimalJSON.age,
                                gender = PetAnimalJSON.gender,
                                size = PetAnimalJSON.size,
                                environment = Environment(
                                        children = PetAnimalJSON.environment?.children,
                                        dog = PetAnimalJSON.environment?.dog,
                                        cat = PetAnimalJSON.environment?.cat
                                ),
                                name = PetAnimalJSON.name,
                                description = PetAnimalJSON.description,
                                photos = PetAnimalJSON.photos?.map { PhotoJSON ->
                                    Photo(
                                            small = PhotoJSON?.small,
                                            medium = PhotoJSON?.medium,
                                            large = PhotoJSON?.large,
                                            full = PhotoJSON?.full
                                    )
                                },
                                contact = Contact(
                                        email = PetAnimalJSON.contact?.email,
                                        phone = PetAnimalJSON.contact?.phone,
                                        address = Adress(
                                                address1 = PetAnimalJSON.contact?.address?.address1,
                                                address2 = PetAnimalJSON.contact?.address?.address2,
                                                city = PetAnimalJSON.contact?.address?.city,
                                                state = PetAnimalJSON.contact?.address?.state,
                                                postCode = PetAnimalJSON.contact?.address?.postCode,
                                                country = PetAnimalJSON.contact?.address?.country
                                        )
                                )
                        )
                    }
                }
                val listNotNullPetAnimal = listPetAnimal?.filterNotNull()
                return PetFinder(listNotNullPetAnimal)
            } else {
                throw CannotDecodeJsonException("adapter from json fail")
            }
        }
    }
}