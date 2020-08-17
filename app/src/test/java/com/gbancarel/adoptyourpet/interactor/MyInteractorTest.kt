package com.tech.myapplication.interactor

import com.gbancarel.adoptyourpet.interactor.MyInteractor
import com.gbancarel.adoptyourpet.interactor.donnes.Contact
import com.gbancarel.adoptyourpet.interactor.donnes.Environment
import com.gbancarel.adoptyourpet.interactor.donnes.PetAnimal
import com.gbancarel.adoptyourpet.interactor.donnes.Photo
import com.gbancarel.adoptyourpet.presenter.MyPresenter
import com.gbancarel.adoptyourpet.repository.MyRepository
import com.gbancarel.adoptyourpet.repository.error.CannotDecodeJsonException
import com.gbancarel.adoptyourpet.repository.error.ErrorStatusException
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.only
import com.nhaarman.mockitokotlin2.then

import org.junit.Test

import org.junit.Before
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class MyInteractorTest {
    @Mock
    private lateinit var repository: MyRepository

    @Mock
    private lateinit var presenter: MyPresenter
    private lateinit var interactor: MyInteractor


    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        interactor = MyInteractor(repository, presenter)
    }

    @Test
    fun getCallSuccess() {
        val animals =
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
                    photos = listOf(Photo("pictureSmall", "pictureMedium", "pictureLarge", "pictureFull")),
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

        // GIVEN
        given(repository.getCall()).willReturn(animals)
        // WHEN
        interactor.getCall()
        // THEN
        then(presenter).should().present(animals)
        then(presenter).shouldHaveNoMoreInteractions()
    }

    @Test
    fun getCallWhenErrorCannotDecodeJsonException() {
        // GIVEN
        given(repository.getCall()).willThrow(CannotDecodeJsonException("Fake reason"))
        // WHEN
        interactor.getCall()
        // THEN
        then(presenter).should(only()).presentErrorOkHttp()
    }

    @Test
    fun getCallWhenErrorStatusException() {
        // GIVEN
        given(repository.getCall()).willThrow(ErrorStatusException("Fake reason"))
        // WHEN
        interactor.getCall()
        // THEN
        then(presenter).should(only()).presentErrorMoshi()
    }

}