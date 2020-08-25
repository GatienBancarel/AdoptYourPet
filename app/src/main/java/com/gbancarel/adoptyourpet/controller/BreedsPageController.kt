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
        Log.i("mylog", "je suis dans le controller")
        interactor.getListBreeds()
    }
}

class BreedsPageControllerDecorator @Inject constructor(val controllerBreed: BreedsPageController) : BreedsPageControllerInterface {
    override fun onCreate() {
        Thread {
            Log.i("mylog", "je suis dans le controller decorator")
            controllerBreed.onCreate()
        }.start()
    }
}