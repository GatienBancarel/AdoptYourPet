package com.gbancarel.adoptyourpet.repository.json.listColors

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TypeJSON (
    val type: ColorsJSON
)