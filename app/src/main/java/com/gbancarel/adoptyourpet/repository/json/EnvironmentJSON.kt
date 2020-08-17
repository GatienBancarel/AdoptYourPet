package com.gbancarel.adoptyourpet.repository.json

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class EnvironmentJSON (
        val children: Boolean?,
        val dog: Boolean?,
        val cat: Boolean?
)