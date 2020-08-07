package com.gbancarel.adoptyourpet.repository.service

import android.util.Log
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import javax.inject.Inject

class TokenService @Inject constructor(
        val client: OkHttpClient // Appel réseau avec OKHttp (library)
) {

    fun getToken(url: String) : Response {
        val CLIENT_ID = "QG7OCwGbN9lGdDdKzVz3Bd15JmGRHqmvnngYBCPeuAEWpBvPD7"
        val CLIENT_SECRET = "EAFrR5SjSU032KcGA4lDDrNLRGiijterccpyf8DO"

        val formBody = FormBody.Builder()
                .add("grant_type", "client_credentials")
                .add("client_id", "$CLIENT_ID")
                .add("client_secret", "$CLIENT_SECRET")
                .build()


        val request = Request.Builder()
                .url(url)
                .post(formBody)
                .build()

        val response = client.newCall(request).execute()
        return Response(
                response.code,
                response.body?.string()
        )
    }
}