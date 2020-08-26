package com.gbancarel.adoptyourpet.controller

import com.gbancarel.adoptyourpet.interactor.BreedsPageInteractor
import com.gbancarel.adoptyourpet.presenter.data.listBreeds.BreedsViewModel
import javax.inject.Inject

interface BreedsPageControllerInterface {
    fun onCreate(selectedBreeds: List<String>)
    fun checkedChange(name: String, selected: Boolean)
}

class BreedsPageController @Inject constructor(
    val interactor: BreedsPageInteractor
) {

    fun onCreate(selectedBreeds: List<String>) {
        interactor.updateListBreeds(selectedBreeds)
    }

    // TODO GBA T.U
    fun checkedChange(name: String, selected: Boolean) {
        interactor.updateBreed(name, selected)
    }
}

class BreedsPageControllerDecorator @Inject constructor(val controllerBreed: BreedsPageController) : BreedsPageControllerInterface {
    override fun onCreate(selectedBreeds: List<String>) {
        Thread {
            controllerBreed.onCreate(selectedBreeds)
        }.start()
    }

    override fun checkedChange(name: String, selected: Boolean) {
        Thread {
            controllerBreed.checkedChange(name, selected)
        }.start()
    }
}