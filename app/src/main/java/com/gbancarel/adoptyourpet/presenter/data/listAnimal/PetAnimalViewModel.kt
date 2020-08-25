package com.gbancarel.adoptyourpet.presenter.data.listAnimal

data class PetAnimalViewModel (
    val name: String,
    val description: String?,
    val photos: List<PhotoViewModel>,
    val shouldShowPhoto: Boolean
)