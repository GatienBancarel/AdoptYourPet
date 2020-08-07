package com.gbancarel.adoptyourpet.repository.json

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PetFinderJSON (
    val animals: List<PetAnimalJSON>
)