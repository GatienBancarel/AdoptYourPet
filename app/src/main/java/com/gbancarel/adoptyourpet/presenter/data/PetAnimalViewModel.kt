package com.gbancarel.adoptyourpet.presenter.data

data class PetAnimalViewModel (
        val name: String,
        val description: String?,
        val photos: List<PhotoViewModel>,
        val shouldShowPhoto: Boolean
)