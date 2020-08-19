package com.gbancarel.adoptyourpet.repository.service

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import dagger.hilt.android.qualifiers.ActivityContext
import okhttp3.OkHttpClient
import okhttp3.Request
import javax.inject.Inject
import kotlin.coroutines.coroutineContext


class PetFinderService @Inject constructor(
    val client: OkHttpClient, // Appel réseau avec OKHttp (library)
    @ActivityContext private val context: Context
) {

    @RequiresApi(Build.VERSION_CODES.M)
    fun get(url: String): ResponseRequest {
        if (isOnline(context = context)) {
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
        } else {
            return ResponseRequest(
                500,
                null
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
            }
        }
        return false
    }
}