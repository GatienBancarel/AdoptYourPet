package com.gbancarel.adoptyourpet.repository.json.listAnimal

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BreedJSON (
        val primary: String?
)