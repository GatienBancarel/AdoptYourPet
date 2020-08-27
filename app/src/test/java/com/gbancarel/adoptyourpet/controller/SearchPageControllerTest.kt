package com.gbancarel.adoptyourpet.controller

import com.gbancarel.adoptyourpet.interactor.SearchPageInteractor
import org.junit.Test
import org.junit.Before
import org.mockito.BDDMockito.then
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class SearchPageControllerTest {

    @Mock
    private lateinit var interactor : SearchPageInteractor
    private lateinit var controller: SearchPageController

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        controller = SearchPageController(interactor)
    }

    @Test
    fun onAnimalCheckboxClicked() {
        // GIVEN
        // WHEN
        controller.onAnimalCheckboxClicked("dog")

        // THEN
        then(interactor).should().getListBreeds("dog")
    }

    @Test
    fun onSelectedBreeds() {
        // GIVEN
        // WHEN
        controller.onSelectedBreeds(listOf("labrador"))
        // THEN
        then(interactor).should().selectBreeds(listOf("labrador"))
    }
}
