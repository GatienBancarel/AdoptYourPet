package com.gbancarel.adoptyourpet.controller

import com.gbancarel.adoptyourpet.interactor.HomePageInteractor
import com.nhaarman.mockitokotlin2.only
import org.junit.Test
import org.junit.Before
import org.mockito.BDDMockito.then
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class HomePageControllerTest {

    @Mock
    private lateinit var interactor : HomePageInteractor
    private lateinit var controller: HomePageController

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        controller = HomePageController(interactor)
    }

    @Test
    fun onCreate() {
        // GIVEN
        // WHEN
        controller.onCreate()

        // THEN
        then(interactor).should(only()).getListAnimal()
    }

    @Test
    fun backClicked() {
        // GIVEN
        // WHEN
        controller.backClicked()

        // THEN
        then(interactor).should(only()).loadPreviousState()
    }

    @Test
    fun clickedRow() {
        // GIVEN
        // WHEN
        controller.clickedRow("name")

        // THEN
        then(interactor).should(only()).loadAnimal("name")
    }
}