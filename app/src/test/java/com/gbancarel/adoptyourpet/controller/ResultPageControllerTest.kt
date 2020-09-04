package com.gbancarel.adoptyourpet.controller

import com.gbancarel.adoptyourpet.interactor.ResultPageInteractor
import com.nhaarman.mockitokotlin2.only
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class ResultPageControllerTest {

    @Mock
    private lateinit var interactor : ResultPageInteractor
    private lateinit var controller: ResultPageController

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        controller = ResultPageController(interactor)
    }

    @Test
    fun onCreate() {
        // GIVEN
        // WHEN
        controller.onCreate(
            animalSelected = "dog",
            breedsSelected = "Pug,Samoyed",
            sizeSelected = "Small",
            ageSelected = "Young",
            colorsSelected = "White",
            genderSelected = "Male"
        )
        // THEN
        BDDMockito.then(interactor).should(only()).getListAnimal(
            animalSelected = "dog",
            breedsSelected = "Pug,Samoyed",
            sizeSelected = "Small",
            ageSelected = "Young",
            colorsSelected = "White",
            genderSelected = "Male"
        )
    }

    @Test
    fun backClicked() {
        // GIVEN
        // WHEN
        controller.backClicked()

        // THEN
        BDDMockito.then(interactor).should(only()).loadPreviousState()
    }

    @Test
    fun clickedRow() {
        // GIVEN
        // WHEN
        controller.clickedRow("name")

        // THEN
        BDDMockito.then(interactor).should(only()).loadAnimal("name")
    }
}