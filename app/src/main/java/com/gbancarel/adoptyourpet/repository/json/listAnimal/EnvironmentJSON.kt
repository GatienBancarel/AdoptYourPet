package com.gbancarel.adoptyourpet.repository.json.listAnimal

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class EnvironmentJSON (
        val children: Boolean?,
        val dog: Boolean?,
        val cat: Boolean?
)