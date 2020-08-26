package com.gbancarel.adoptyourpet.controller

import com.gbancarel.adoptyourpet.interactor.BreedsPageInteractor
import com.gbancarel.adoptyourpet.presenter.data.listBreeds.BreedsViewModel
import javax.inject.Inject

interface BreedsPageControllerInterface {
    fun onCreate()
    fun checkedChange(name: String, selected: Boolean)
}

class BreedsPageController @Inject constructor(
    val interactor: BreedsPageInteractor
) {

    fun onCreate() {
        interactor.getListBreeds()
    }

    // TODO GBA T.U
    fun checkedChange(name: String, selected: Boolean) {
        interactor.update(name, selected)
    }
}

class BreedsPageControllerDecorator @Inject constructor(val controllerBreed: BreedsPageController) : BreedsPageControllerInterface {
    override fun onCreate() {
        Thread {
            controllerBreed.onCreate()
        }.start()
    }

    override fun checkedChange(name: String, selected: Boolean) {
        Thread {
            controllerBreed.checkedChange(name, selected)
        }.start()
    }
}