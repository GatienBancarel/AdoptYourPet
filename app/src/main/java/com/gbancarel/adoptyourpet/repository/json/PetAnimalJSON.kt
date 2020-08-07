package com.gbancarel.adoptyourpet.repository.json

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PetAnimalJSON (
    val type: String?,
    val age: String?,
    val gender: String?,
    val size: String?,
    val name: String?,
    val description: String?
)