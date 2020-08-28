package com.gbancarel.adoptyourpet.controller

import com.gbancarel.adoptyourpet.interactor.SearchPageInteractor
import com.gbancarel.adoptyourpet.state.AnimalSelected
import javax.inject.Inject

interface SearchPageControllerInterface {
    fun onAnimalCheckboxClicked(animalSelected: AnimalSelected)
    fun onSelectedBreeds(breeds: List<String>)
    fun onSelectedSize(id: Int)
    fun onSelectedAge(age: String, selected: Boolean, order: Int)
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

    fun onSelectedAge(age: String, selected: Boolean, order: Int) {
        interactor.selectedAge(age, selected,order)
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

    override fun onSelectedAge(age: String, selected: Boolean, order: Int) {
        Thread {
            controller.onSelectedAge(age, selected, order)
        }.start()
    }
}