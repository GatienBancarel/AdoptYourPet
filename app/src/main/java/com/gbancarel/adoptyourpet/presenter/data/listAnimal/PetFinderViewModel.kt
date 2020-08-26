package com.gbancarel.adoptyourpet.presenter.data.listAnimal

data class PetFinderViewModel (
    val animals: List<PetAnimalViewModel>,
    val state: PetFinderViewModelState
)