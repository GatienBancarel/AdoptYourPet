package com.gbancarel.adoptyourpet.repository.service

import android.util.Log
import com.gbancarel.adoptyourpet.repository.MyRepository
import com.gbancarel.adoptyourpet.repository.error.ErrorStatusException
import com.gbancarel.adoptyourpet.repository.parser.TokenParser

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import javax.inject.Inject

class MyInterceptor @Inject constructor(authToken: String?) : Interceptor {

    val currentToken = authToken

    override fun intercept(chain: Interceptor.Chain): Response {

        val accessToken = currentToken
        val request = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer $accessToken")
                .build()
        val response = chain.proceed(request)

        if (response.code == 401) {
            val newToken: String? = newToken()
            if (newToken != null) {
                val newRequest =  chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer $newToken")
                        .build()
                return chain.proceed(newRequest)
            }
        }
        return response
    }

    fun newToken () :String? {
        val tokenService: TokenService = TokenService(OkHttpClient.Builder().build())
        val tokenParser: TokenParser = TokenParser()
        val token = tokenService.getToken("https://api.petfinder.com/v2/oauth2/token")

        if (token.statusCode != 200 && token.statusCode != 201) {
            throw ErrorStatusException("http request fail for token")
        } else {
            val tokenEntityJSON = tokenParser.parse(token.body)
            return tokenEntityJSON?.access_token
        }
    }
}