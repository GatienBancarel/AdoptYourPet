package com.gbancarel.adoptyourpet.controller

import com.gbancarel.adoptyourpet.interactor.SearchPageInteractor
import javax.inject.Inject

interface SearchPageControllerInterface {
    fun onAnimalCheckboxClicked(animalSelected: String)
    fun onSelectedBreeds(breeds: List<String>)
}

class SearchPageController @Inject constructor(
    val interactor: SearchPageInteractor
) {

    fun onAnimalCheckboxClicked(animalSelected: String) {
        interactor.getListBreeds(animalSelected)
    }

    fun onSelectedBreeds(breeds: List<String>) {
        interactor.selectBreeds(breeds)
    }
}

class SearchPageControllerDecorator @Inject constructor(val controller: SearchPageController) : SearchPageControllerInterface {
    override fun onAnimalCheckboxClicked(animalSelected: String) {
        Thread {
            controller.onAnimalCheckboxClicked(animalSelected)
        }.start()
    }

    override fun onSelectedBreeds(breeds: List<String>) {
        Thread {
            controller.onSelectedBreeds(breeds)
        }.start()
    }
}