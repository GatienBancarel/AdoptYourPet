package com.gbancarel.adoptyourpet.repository.json.listAnimal

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PetAnimalJSON (
    val type: String?,
    val breeds: BreedJSON?,
    val colors: ColorJSON?,
    val age: String?,
    val gender: String?,
    val size: String?,
    val environment: EnvironmentJSON?,
    val name: String?,
    val description: String?,
    val photos: List<PhotoJSON>?,
    val contact: ContactJSON?
)