package com.gbancarel.adoptyourpet.repository.json.listBreeds

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ListBreedsJSON(
    val breeds: List<BreedsJSON?>
)