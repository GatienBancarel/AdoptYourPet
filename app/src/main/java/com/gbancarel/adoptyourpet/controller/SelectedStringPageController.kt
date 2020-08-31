package com.gbancarel.adoptyourpet.controller

import com.gbancarel.adoptyourpet.interactor.SelectedStringPageInteractor
import javax.inject.Inject

interface BreedsPageControllerInterface {
    fun onCreate(selectedBreeds: List<String>)
    fun checkedChange(name: String, selected: Boolean)
}

class SelectedStringPageController @Inject constructor(
    val interactor: SelectedStringPageInteractor
) {

    fun onCreate(selectedString: List<String>) {
        interactor.updateListBreeds(selectedString)
    }

    fun checkedChange(name: String, selected: Boolean) {
        interactor.updateBreed(name, selected)
    }
}

class SelectedStringPageControllerDecorator @Inject constructor(val controller: SelectedStringPageController) : BreedsPageControllerInterface {
    override fun onCreate(selectedString: List<String>) {
        Thread {
            controller.onCreate(selectedString)
        }.start()
    }

    override fun checkedChange(name: String, selected: Boolean) {
        Thread {
            controller.checkedChange(name, selected)
        }.start()
    }
}