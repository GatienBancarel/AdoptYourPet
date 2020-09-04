package com.gbancarel.adoptyourpet.presenter.data.listAnimal

data class PetFinderViewModelData(
    val animals: List<PetAnimalViewModel>,
    val state: PetFinderViewModelState,
    val detailAnimal: PetDetailViewModelData?
)

data class PetDetailViewModelData(
    val name: String,
    val age: Int?,
    val type: Int?,
    val breed: String?,
    val color: String,
    val size: Int?,
    val gender: Int?,
    val description: String?,
    val photo: String?,
    val address: String?,
    val phone: String?,
    val email: String?
)