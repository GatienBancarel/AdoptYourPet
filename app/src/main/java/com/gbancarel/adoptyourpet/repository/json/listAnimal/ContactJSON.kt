package com.gbancarel.adoptyourpet.repository.json.listAnimal

import com.gbancarel.adoptyourpet.repository.json.listAnimal.AdressJSON
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ContactJSON (
        val email: String?,
        val phone: String?,
        val address: AdressJSON?
)