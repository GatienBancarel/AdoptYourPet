package com.gbancarel.adoptyourpet.controller

import com.gbancarel.adoptyourpet.interactor.SelectedStringPageInteractor
import org.junit.Test
import org.junit.Before
import org.mockito.BDDMockito.then
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class SelectedStringPageControllerTest {

    @Mock
    private lateinit var interactor : SelectedStringPageInteractor
    private lateinit var controller: SelectedStringPageController

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        controller = SelectedStringPageController(interactor)
    }

    @Test
    fun onCreate() {
        // GIVEN
        // WHEN
        controller.onCreate(listOf("labrador","caniche"),"breeds")

        // THEN
        then(interactor).should().updateListString(listOf("labrador","caniche"),"breeds")
    }

    @Test
    fun checkedChange() {
        //WHEN
        controller.checkedChange("labrador",true)
        //THEN
        then(interactor).should().updateBreed("labrador",true)
    }
}