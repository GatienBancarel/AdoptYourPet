package com.gbancarel.adoptyourpet.controller

import com.gbancarel.adoptyourpet.interactor.SearchPageInteractor
import javax.inject.Inject

interface SearchPageControllerInterface {
    fun onAnimalCheckboxClicked(s: String)
}

class SearchPageController @Inject constructor(
    val interactor: SearchPageInteractor
) {

    fun onCreate(animalSelected: String) {
        interactor.getListBreeds(animalSelected)
    }
}

class SearchPageControllerDecorator @Inject constructor(val controller: SearchPageController) : SearchPageControllerInterface {
    override fun onAnimalCheckboxClicked(animalSelected: String) {
        Thread {
            controller.onCreate(animalSelected)
        }.start()
    }
}