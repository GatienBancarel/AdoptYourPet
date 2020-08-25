package com.gbancarel.adoptyourpet.controller

import com.gbancarel.adoptyourpet.interactor.BreedsPageInteractor
import org.junit.Test
import org.junit.Before
import org.mockito.BDDMockito.then
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class BreedsPageControllerTest {

    @Mock
    private lateinit var interactor : BreedsPageInteractor
    private lateinit var controller: BreedsPageController

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        controller = BreedsPageController(interactor)
    }

    @Test
    fun getInteractor() {
        // GIVEN
        // WHEN
        controller.onCreate()

        // THEN
        then(interactor).should().getListBreeds()
    }
}