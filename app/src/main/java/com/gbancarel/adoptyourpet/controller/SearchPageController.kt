package com.gbancarel.adoptyourpet.controller

import com.gbancarel.adoptyourpet.interactor.SearchPageInteractor
import com.gbancarel.adoptyourpet.state.AnimalSelected
import javax.inject.Inject

interface SearchPageControllerInterface {
    fun onAnimalCheckboxClicked(animalSelected: AnimalSelected)
    fun onSelectedBreeds(breeds: List<String>)
    fun onSelectedSize(id: Int)
    fun onSelectedAge(id: Int)
}

class SearchPageController @Inject constructor(
    val interactor: SearchPageInteractor
) {

    fun onAnimalCheckboxClicked(animalSelected: AnimalSelected) {
        interactor.load(animalSelected)
    }

    fun onSelectedBreeds(breeds: List<String>) {
        interactor.selectBreeds(breeds)
    }

    fun onSelectedSize(id: Int) {
        interactor.selectedSize(id)
    }

    fun onSelectedAge(id: Int) {
        interactor.selectedAge(id)
    }

    fun onSelectedColors(ids) {
        interactor.selectedColors(colors, selected)
    }
}

class SearchPageControllerDecorator @Inject constructor(val controller: SearchPageController) : SearchPageControllerInterface {
    override fun onAnimalCheckboxClicked(animalSelected: AnimalSelected) {
        Thread {
            controller.onAnimalCheckboxClicked(animalSelected)
        }.start()
    }

    override fun onSelectedBreeds(breeds: List<String>) {
        Thread {
            controller.onSelectedBreeds(breeds)
        }.start()
    }

    override fun onSelectedSize(id: Int) {
        Thread {
            controller.onSelectedSize(id)
        }.start()
    }

    override fun onSelectedAge(id: Int) {
        Thread {
            controller.onSelectedAge(id)
        }.start()
    }

    override fun onSelectedColors(colors: String, selected: Boolean) {
        Thread {
            controller.onSelectedColors(colors, selected)
        }.start()
    }
}