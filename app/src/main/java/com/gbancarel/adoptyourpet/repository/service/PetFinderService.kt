package com.gbancarel.adoptyourpet.repository.service

import okhttp3.OkHttpClient
import okhttp3.Request
import javax.inject.Inject

class PetFinderService @Inject constructor(
    val client: OkHttpClient // Appel réseau avec OKHttp (library)
) {

    fun get(url: String, authToken: String?) : Response {

        val request = Request.Builder()
                .url(url)
                //.addHeader("Accept-Language", Locale.getDefault().language)
                .addHeader("Authorization", "Bearer $authToken")
                .build()
        val response = client.newCall(request).execute()
        return Response(
                response.code,
                response.body?.string()
        )
    }
}
