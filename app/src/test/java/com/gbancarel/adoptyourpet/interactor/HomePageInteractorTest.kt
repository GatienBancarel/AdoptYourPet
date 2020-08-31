package com.tech.myapplication.interactor

import com.gbancarel.adoptyourpet.interactor.HomePageInteractor
import com.gbancarel.adoptyourpet.interactor.data.listAnimal.Contact
import com.gbancarel.adoptyourpet.interactor.data.listAnimal.Environment
import com.gbancarel.adoptyourpet.interactor.data.listAnimal.PetAnimal
import com.gbancarel.adoptyourpet.interactor.data.listAnimal.Photo
import com.gbancarel.adoptyourpet.presenter.HomePagePresenter
import com.gbancarel.adoptyourpet.repository.ListPetRepository
import com.gbancarel.adoptyourpet.repository.error.CannotDecodeJsonException
import com.gbancarel.adoptyourpet.repository.error.ErrorStatusException
import com.gbancarel.adoptyourpet.repository.error.NoInternetConnectionAvailable
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.only
import com.nhaarman.mockitokotlin2.then
import org.junit.Test
import org.junit.Before
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class HomePageInteractorTest {
    @Mock
    private lateinit var repository: ListPetRepository

    @Mock
    private lateinit var presenter: HomePagePresenter
    private lateinit var interactor: HomePageInteractor
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
        interactor = HomePageInteractor(repository, presenter)
    }

    @Test
    fun getCallSuccess() {
        // GIVEN
        given(repository.getListAnimal()).willReturn(animals)
        // WHEN
        interactor.getListAnimal()
        // THEN
        then(presenter).should().present(animals)
    }

    @Test
    fun getCallWhenErrorCannotDecodeJsonException() {
        // GIVEN
        given(repository.getListAnimal()).willThrow(CannotDecodeJsonException("Fake reason"))
        // WHEN
        interactor.getListAnimal()
        // THEN
        then(presenter).should().presentError()
    }

    @Test
    fun getCallWhenErrorStatusException() {
        // GIVEN
        given(repository.getListAnimal()).willThrow(ErrorStatusException("Fake reason"))
        // WHEN
        interactor.getListAnimal()
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