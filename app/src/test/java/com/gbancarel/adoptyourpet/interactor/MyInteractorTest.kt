package com.tech.myapplication.interactor

import com.gbancarel.adoptyourpet.interactor.MyInteractor
import com.gbancarel.adoptyourpet.interactor.donnes.PetAnimal
import com.gbancarel.adoptyourpet.interactor.donnes.PetFinder
import com.gbancarel.adoptyourpet.presenter.MyPresenter
import com.gbancarel.adoptyourpet.repository.MyRepository
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.then

import org.junit.Test

import org.junit.Before
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class MyInteractorTest {
    @Mock
    private lateinit var repository : MyRepository
    @Mock
    private lateinit var presenter : MyPresenter
    private lateinit var interactor: MyInteractor


    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        interactor = MyInteractor(repository,presenter)
    }

    @Test
    fun getCallSucess() {
        val call = PetFinder(
                listOf(
                        PetAnimal(
                                type = "dog",
                                age = "young",
                                gender = "male",
                                size = "meduim",
                                name = "rex",
                                description = "description du chien"),
                        PetAnimal(
                                type = "dog",
                                age = "young",
                                gender = "male",
                                size = "meduim",
                                name = "rex",
                                description = "description du chien")
                )
        )

        // GIVEN
        given(repository.getCall()).willReturn(call)
        // WHEN
        interactor.getCall()
        // THEN
        then(presenter).should().present(call)
    }

}