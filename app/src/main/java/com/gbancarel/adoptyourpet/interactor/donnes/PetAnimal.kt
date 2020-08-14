package com.gbancarel.adoptyourpet.interactor.donnes

data class PetAnimal (
        val type: String?,
        val breeds: Breed?,
        val colors: Color?,
        val age: String?,
        val gender: String?,
        val size: String?,
        val environment: Environment?,
        val name: String?,
        val description: String?,
        val photos: List<Photo?>?,
        val contact: Contact?
)