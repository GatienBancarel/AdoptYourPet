package com.gbancarel.adoptyourpet.repository.service

import android.util.Log
import com.gbancarel.adoptyourpet.repository.MyRepository
import okhttp3.*
import javax.inject.Inject

class PetFinderService @Inject constructor(
    val client: OkHttpClient // Appel réseau avec OKHttp (library)
) {

    fun get(url: String, authToken: String?) : ResponseRequest {

        val request = Request.Builder()
                .url(url)
                .build()
        val response = client
                .newBuilder()
                .addInterceptor(MyInterceptor(authToken))
                .build()
                .newCall(request)
                .execute()

        return ResponseRequest(
                response.code,
                response.body?.string()
        )
    }
}