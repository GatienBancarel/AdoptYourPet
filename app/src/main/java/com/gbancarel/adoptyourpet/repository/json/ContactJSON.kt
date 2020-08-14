package com.gbancarel.adoptyourpet.repository.json

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ContactJSON (
        val email: String?,
        val phone: String?,
        val address: AdressJSON?
)