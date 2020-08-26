package com.gbancarel.adoptyourpet.repository.json.listAnimal

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PhotoJSON (
        val small: String,
        val medium: String,
        val large: String,
        val full: String
)