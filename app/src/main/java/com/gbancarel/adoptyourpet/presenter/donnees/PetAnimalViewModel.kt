package com.gbancarel.adoptyourpet.presenter.donnees

data class PetAnimalViewModel (
        val name: String,
        val description: String?,
        val photos: List<PhotoViewModel>,
        val shouldShowPhoto: Boolean
)