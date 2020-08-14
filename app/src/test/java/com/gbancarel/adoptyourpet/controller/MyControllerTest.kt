package com.gbancarel.adoptyourpet.controller


import com.gbancarel.adoptyourpet.interactor.MyInteractor
import org.junit.Test

import org.junit.Before
import org.mockito.BDDMockito.then
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class MyControllerTest {

    @Mock
    private lateinit var interactor : MyInteractor
    private lateinit var controller: MyController

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        controller = MyController(interactor)
    }

    @Test
    fun getInteractor() {
        // GIVEN
        // WHEN
        controller.onCreate()

        // THEN
        then(interactor).should().getCall()
    }
}