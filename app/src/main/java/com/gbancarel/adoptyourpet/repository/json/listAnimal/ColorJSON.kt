package com.gbancarel.adoptyourpet.repository.json.listAnimal

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ColorJSON (
        val primary: String?
)