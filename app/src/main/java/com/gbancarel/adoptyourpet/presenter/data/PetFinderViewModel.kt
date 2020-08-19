package com.gbancarel.adoptyourpet.presenter.data

data class PetFinderViewModel (
    val animals: List<PetAnimalViewModel>,
    var loader: Boolean = true,
    var error: Boolean = false
)