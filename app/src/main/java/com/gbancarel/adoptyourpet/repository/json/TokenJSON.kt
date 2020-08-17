package com.gbancarel.adoptyourpet.repository.json

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TokenJSON (
        val token_type: String?,
        val expires_in: String?,
        val access_token: String?
)