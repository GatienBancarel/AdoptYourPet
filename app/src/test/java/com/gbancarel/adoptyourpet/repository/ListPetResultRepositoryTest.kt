package com.gbancarel.adoptyourpet.repository

import com.gbancarel.adoptyourpet.interactor.data.listAnimal.*
import com.gbancarel.adoptyourpet.repository.dao.AnimalResultDao
import com.gbancarel.adoptyourpet.repository.error.CannotDecodeJsonException
import com.gbancarel.adoptyourpet.repository.error.ErrorStatusException
import com.gbancarel.adoptyourpet.repository.json.listAnimal.*
import com.gbancarel.adoptyourpet.repository.local.*
import com.gbancarel.adoptyourpet.repository.parser.PetFinderParser
import com.gbancarel.adoptyourpet.repository.service.PetFinderService
import com.gbancarel.adoptyourpet.repository.service.ResponseRequest
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.then
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class ListPetResultRepositoryTest {
    @Mock
    lateinit var petFinderService: PetFinderService

    @Mock
    lateinit var petFinderParser: PetFinderParser

    @Mock
    lateinit var dao: AnimalResultDao

    lateinit var repository: ListPetResultRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        repository = ListPetResultRepository(petFinderService, petFinderParser, dao)
    }

    @Test
    fun getCall_whenGetCallSuccess() {
        // GIVEN
        given(petFinderService.get("https://api.petfinder.com/v2/animals?type=dog&breed=Pug,Samoyed&age=Young&gender=Male&limit=100"))
            .willReturn(
                ResponseRequest(
                    200,
                    "body200"
                )
            )
        given(
            petFinderParser.parse(
                "body200"
            )
        )
            .willReturn(
                PetFinderJSON(
                    listOf(
                        PetAnimalJSON(
                            "Dog",
                            BreedJSON("Labrador"),
                            ColorJSON("brown"),
                            "Young",
                            "Male",
                            "Medium",
                            EnvironmentJSON(true, true, true),
                            "Rex",
                            "description",
                            listOf(
                                PhotoJSON(
                                    "pictureSmall",
                                    "pictureMedium",
                                    "pictureLarge",
                                    "pictureFull"
                                )
                            ),
                            ContactJSON(
                                "test@test.fr",
                                "010101010101",
                                AdressJSON(
                                    "24 rue yves montand",
                                    null,
                                    "Bourges",
                                    "Centre",
                                    "18000",
                                    "France"
                                )
                            )
                        )
                    )
                )
            )

        // WHEN
        val call = repository.getListAnimal(
            animalSelected = "dog",
            breedsSelected = "Pug,Samoyed",
            sizeSelected = "Small",
            ageSelected = "Young",
            colorsSelected = "White",
            genderSelected = "Male"
        )

        // THEN
        then(dao).should().deleteAll()
        then(dao).should().insertAll(
            listOf(
                PetAnimalLocal(
                    type = "Dog",
                    breed = "Labrador",
                    color = "brown",
                    age = "Young",
                    gender = "Male",
                    size = "Medium",
                    name = "Rex",
                    description = "description",
                    photo =
                    PhotoLocal(
                        "pictureSmall",
                        "pictureMedium",
                        "pictureLarge",
                        "pictureFull"
                    ),
                    environment = EnvironmentLocal(true, true, true),
                    contact = ContactLocal(
                        "test@test.fr", "010101010101", AddressLocal(
                            "24 rue yves montand",
                            null,
                            "Bourges",
                            "Centre",
                            "18000",
                            "France"
                        )
                    )
                )
            )
        )
        Assert.assertEquals(
            call,
            listOf(
                PetAnimal(
                    "Dog",
                    "Labrador",
                    "brown",
                    "Young",
                    "Male",
                    "Medium",
                    Environment(true, true, true),
                    "Rex",
                    "description",
                    listOf(
                        Photo(
                            "pictureSmall",
                            "pictureMedium",
                            "pictureLarge",
                            "pictureFull"
                        )
                    ),
                    Contact(
                        "test@test.fr",
                        "010101010101",
                        Address(
                            "24 rue yves montand",
                            null,
                            "Bourges",
                            "Centre",
                            "18000",
                            "France"
                        )
                    )
                )
            )
        )
    }

    @Test(expected = ErrorStatusException::class)
    fun getCall_whenGetCallWrongStatus() {
        // GIVEN
        given(
            petFinderService.get(
                "https://api.petfinder.com/v2/animals?type=dog&breed=Pug,Samoyed&age=Young&gender=Male&limit=100"
            )
        ).willReturn(
            ResponseRequest(
                401,
                "body401"
            )
        )

        // WHEN
        repository.getListAnimal(
            animalSelected = "dog",
            breedsSelected = "Pug,Samoyed",
            sizeSelected = "Small",
            ageSelected = "Young",
            colorsSelected = "White",
            genderSelected = "Male"
        )
    }


    @Test(expected = CannotDecodeJsonException::class)
    fun getCall_whenGetCallCannotDecodeJson() {
        // GIVEN
        given(
            petFinderService.get(
                "https://api.petfinder.com/v2/animals?type=dog&breed=Pug,Samoyed&age=Young&gender=Male&limit=100"
            )
        ).willReturn(
            ResponseRequest(
                200,
                "body200"
            )
        )
        given(
            petFinderParser.parse(
                "body200"
            )
        ).willReturn(null)

        // WHEN
        repository.getListAnimal(
            animalSelected = "dog",
            breedsSelected = "Pug,Samoyed",
            sizeSelected = "Small",
            ageSelected = "Young",
            colorsSelected = "White",
            genderSelected = "Male"
        )
    }

    @Test
    fun getLocalListAnimal() {
        // GIVEN
        given(dao.getAll()).willReturn(
            listOf(
                PetAnimalLocal(
                    type = "dog",
                    breed = "labrador",
                    color = "brown",
                    age = "young",
                    gender = "male",
                    size = "meduim",
                    name = "rex",
                    description = "description du chien",
                    photo =
                    PhotoLocal(
                        "pictureSmall",
                        "pictureMedium",
                        "pictureLarge",
                        "pictureFull"
                    ),
                    environment = EnvironmentLocal(true, true, true),
                    contact = ContactLocal("test@test.fr", null, null)
                )
            )
        )

        // WHEN
        val result = repository.getLocalListAnimal()

        // THEN
        Assert.assertEquals(
            result, listOf(
                PetAnimal(
                    type = "dog",
                    breed = "labrador",
                    color = "brown",
                    age = "young",
                    gender = "male",
                    size = "meduim",
                    name = "rex",
                    description = "description du chien",
                    photos = listOf(
                        Photo(
                            "pictureSmall",
                            "pictureMedium",
                            "pictureLarge",
                            "pictureFull"
                        )
                    ),
                    environment = Environment(true, true, true),
                    contact = Contact("test@test.fr", null, null)
                )
            )
        )
    }
}