package com.gbancarel.adoptyourpet.repository.json.listColors

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ColorsJSON (
    val colors: List<String>
)