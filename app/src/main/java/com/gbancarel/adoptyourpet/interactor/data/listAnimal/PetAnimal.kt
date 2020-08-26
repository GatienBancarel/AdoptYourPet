package com.gbancarel.adoptyourpet.interactor.data.listAnimal

data class PetAnimal(
        val type: String,
        val breed: String?,
        val color: String?,
        val age: String?,
        val gender: String?,
        val size: String?,
        val environment: Environment?,
        val name: String,
        val description: String?,
        val photos: List<Photo>,
        val contact: Contact?
)
