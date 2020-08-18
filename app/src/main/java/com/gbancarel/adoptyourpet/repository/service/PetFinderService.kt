package com.gbancarel.adoptyourpet.repository.service

import okhttp3.*
import javax.inject.Inject

class PetFinderService @Inject constructor(
    val client: OkHttpClient // Appel réseau avec OKHttp (library)
) {

    fun get(url: String): ResponseRequest {
        val response = client
            .newCall(
                Request.Builder()
                    .url(url)
                    .build()
            )
            .execute()

        return ResponseRequest(
            response.code,
            response.body?.string()
        )
    }
}