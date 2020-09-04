package com.gbancarel.adoptyourpet.interactor.data

import com.gbancarel.adoptyourpet.presenter.data.listGender.GenderViewModel
import com.gbancarel.adoptyourpet.state.AnimalSelected
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchSession @Inject constructor() {
    private var animalType: AnimalSelected = AnimalSelected.dog
    private var selectedGender: GenderViewModel? = null
    private var ages: List<Age> = emptyList()
    private var selectedBreeds: List<String> = emptyList()
    private var selectedColors: List<String> = emptyList()
    private var size: List<Size> = emptyList()

    fun set(
        animalType: AnimalSelected,
        selectedGender: GenderViewModel,
        ages: List<Age>,
        selectedBreeds: List<String>,
        selectedColors: List<String>,
        size: List<Size>
    ) {
        this.animalType = animalType
        this.selectedGender = selectedGender
        this.ages = ages
        this.selectedBreeds = selectedBreeds
        this.selectedColors = selectedColors
        this.size = size
    }

    fun getAnimalType() = animalType
    fun getSelectedGender() = selectedGender
    fun getAges() = ages
    fun getSelectedBreeds() = selectedBreeds
    fun getSelectedColors() = selectedColors
    fun getSize() = size

    fun dispose() {
        animalType = AnimalSelected.dog
        selectedGender = null
        ages = emptyList()
        selectedBreeds = emptyList()
        selectedColors = emptyList()
        size = emptyList()
    }

}