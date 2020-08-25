package com.gbancarel.adoptyourpet.repository.json.listAnimal

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PetFinderJSON (
    val animals: List<PetAnimalJSON?>
)