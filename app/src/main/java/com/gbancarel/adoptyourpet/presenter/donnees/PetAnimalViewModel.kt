package com.gbancarel.adoptyourpet.presenter.donnees

class PetAnimalViewModel (
        val type: String?,
        val breeds: BreedViewModel?,
        val colors: ColorViewModel?,
        val age: String?,
        val gender: String?,
        val size: String?,
        val environment: EnvironmentViewModel?,
        val name: String?,
        val description: String?,
        val photos: List<PhotoViewModel?>?,
        val contact: ContactViewModel?
)