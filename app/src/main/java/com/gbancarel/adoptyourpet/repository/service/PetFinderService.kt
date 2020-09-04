package com.gbancarel.adoptyourpet.repository.service

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import com.gbancarel.adoptyourpet.repository.error.NoInternetConnectionAvailable
import dagger.hilt.android.qualifiers.ActivityContext
import okhttp3.OkHttpClient
import okhttp3.Request
import javax.inject.Inject
import kotlin.jvm.Throws


class PetFinderService @Inject constructor(
    val client: OkHttpClient, // Appel réseau avec OKHttp (library)
    @ActivityContext private val context: Context
) {

    @Throws(
        NoInternetConnectionAvailable::class
    )
    fun get(url: String): ResponseRequest {
        if (isOnline(context = context)) {
            val response = client
                .newCall(
                    Request.Builder()
                        .url(url)
                        .build()
                )
                .execute()
            val str = response.body?.string()
            Log.i("PBA", str+"")
            return ResponseRequest(
                response.code,
                str
            )
        } else {
            throw NoInternetConnectionAvailable("No Internet")
        }
    }


    private fun isOnline(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        return true
                    }
                }
            }
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                return true
            }
        }
        return false
    }
}