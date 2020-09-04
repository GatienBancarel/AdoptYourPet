package com.gbancarel.adoptyourpet.interactor

import com.gbancarel.adoptyourpet.interactor.data.listAnimal.Contact
import com.gbancarel.adoptyourpet.interactor.data.listAnimal.Environment
import com.gbancarel.adoptyourpet.interactor.data.listAnimal.PetAnimal
import com.gbancarel.adoptyourpet.interactor.data.listAnimal.Photo
import com.gbancarel.adoptyourpet.presenter.ResultPagePresenter
import com.gbancarel.adoptyourpet.repository.ListPetResultRepository
import com.gbancarel.adoptyourpet.repository.error.CannotDecodeJsonException
import com.gbancarel.adoptyourpet.repository.error.ErrorStatusException
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.only
import com.nhaarman.mockitokotlin2.then
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class ResultPageInteractorTest {
    @Mock
    private lateinit var repository: ListPetResultRepository

    @Mock
    private lateinit var presenter: ResultPagePresenter
    private lateinit var interactor: ResultPageInteractor
    private val animals =
        listOf(
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
            ),
            PetAnimal(
                type = "dog",
                breed = "labrador",
                color = "brown",
                age = "young",
                gender = "male",
                size = "meduim",
                name = "rex",
                description = "description du chien",
                photos = emptyList(),
                environment = Environment(true, true, true),
                contact = Contact("test@test.fr", null, null)
            )
        )

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        interactor = ResultPageInteractor(repository, presenter)
    }

    @Test
    fun getCallSuccess() {
        // GIVEN
        given(
            repository.getListAnimal(
                animalSelected = "dog",
                breedsSelected = "Pug,Samoyed",
                sizeSelected = "Small",
                ageSelected = "Young",
                colorsSelected = "White",
                genderSelected = "Male"
            )
        ).willReturn(animals)
        // WHEN
        interactor.getListAnimal(
            animalSelected = "dog",
            breedsSelected = "Pug,Samoyed",
            sizeSelected = "Small",
            ageSelected = "Young",
            colorsSelected = "White",
            genderSelected = "Male"
        )
        // THEN
        then(presenter).should().present(animals)
    }

    @Test
    fun getCallWhenErrorCannotDecodeJsonException() {
        // GIVEN
        given(
            repository.getListAnimal(animalSelected = "dog",
                breedsSelected = "Pug,Samoyed",
                sizeSelected = "Small",
                ageSelected = "Young",
                colorsSelected = "White",
                genderSelected = "Male")
        ).willThrow(CannotDecodeJsonException("Fake reason"))
        // WHEN
        interactor.getListAnimal(
            animalSelected = "dog",
            breedsSelected = "Pug,Samoyed",
            sizeSelected = "Small",
            ageSelected = "Young",
            colorsSelected = "White",
            genderSelected = "Male"
        )
        // THEN
        then(presenter).should().presentError()
    }

    @Test
    fun getCallWhenErrorStatusException() {
        // GIVEN
        given(
            repository.getListAnimal(
                animalSelected = "dog",
                breedsSelected = "Pug,Samoyed",
                sizeSelected = "Small",
                ageSelected = "Young",
                colorsSelected = "White",
                genderSelected = "Male"
            )
        ).willThrow(ErrorStatusException("Fake reason"))
        // WHEN
        interactor.getListAnimal(
            animalSelected = "dog",
            breedsSelected = "Pug,Samoyed",
            sizeSelected = "Small",
            ageSelected = "Young",
            colorsSelected = "White",
            genderSelected = "Male"
        )
        // THEN
        then(presenter).should().presentError()
    }

    @Test
    fun loadPreviousState() {
        // GIVEN
        given(repository.getLocalListAnimal()).willReturn(animals)
        // WHEN
        interactor.loadPreviousState()
        // THEN
        then(presenter).should(only()).present(animals)
    }


    @Test
    fun loadAnimal() {
        // GIVEN
        given(repository.getLocalListAnimal()).willReturn(animals)
        // WHEN
        interactor.loadAnimal("rex")
        // THEN
        then(presenter).should(only()).present(
            animals,
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
    }

}