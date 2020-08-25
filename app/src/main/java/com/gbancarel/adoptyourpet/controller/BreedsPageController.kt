package com.gbancarel.adoptyourpet.controller

import android.util.Log
import com.gbancarel.adoptyourpet.interactor.BreedsPageInteractor
import javax.inject.Inject

interface BreedsPageControllerInterface {
    fun onCreate()
}

class BreedsPageController @Inject constructor(
    val interactor: BreedsPageInteractor
) {

    fun onCreate() {
        interactor.getListBreeds()
    }
}

class BreedsPageControllerDecorator @Inject constructor(val controllerBreed: BreedsPageController) : BreedsPageControllerInterface {
    override fun onCreate() {
        Thread {
            controllerBreed.onCreate()
        }.start()
    }
}