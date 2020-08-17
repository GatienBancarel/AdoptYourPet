package com.gbancarel.adoptyourpet.repository.json

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AdressJSON (
        val address1: String?,
        val address2: String?,
        val city: String?,
        val state: String?,
        val postCode: String?,
        val country: String?
)