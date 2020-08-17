package com.gbancarel.adoptyourpet.repository.json

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BreedJSON (
        val primary: String?
)