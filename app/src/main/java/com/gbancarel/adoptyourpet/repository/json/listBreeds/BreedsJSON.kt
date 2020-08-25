package com.gbancarel.adoptyourpet.repository.json.listBreeds

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BreedsJSON (
    val name : String
)