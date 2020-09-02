package com.gbancarel.adoptyourpet.controller

import com.gbancarel.adoptyourpet.interactor.SearchPageInteractor
import com.gbancarel.adoptyourpet.state.AnimalSelected
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
        controller.onAnimalCheckboxClicked(AnimalSelected.dog)

        // THEN
        then(interactor).should().load(AnimalSelected.dog)
    }

    @Test
    fun onSelectedBreeds() {
        // GIVEN
        // WHEN
        controller.onSelectedBreeds(listOf("labrador"))
        // THEN
        then(interactor).should().selectBreeds(listOf("labrador"))
    }

    @Test
    fun onSelectedSize() {
        // GIVEN
        // WHEN
        controller.onSelectedSize(0)
        // THEN
        then(interactor).should().selectedSize(0)
    }

    @Test
    fun onSelectedAge() {
        // GIVEN
        // WHEN
        controller.onSelectedAge(1)
        // THEN
        then(interactor).should().selectedAge(1)
    }

    @Test
    fun onSelectedColors() {
        // GIVEN
        // WHEN
        controller.onSelectedColors(listOf("brown"))
        // THEN
        then(interactor).should().selectedColors(listOf("brown"))
    }
}
