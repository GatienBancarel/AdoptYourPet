package com.gbancarel.adoptyourpet.repository

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import javax.inject.Inject

class PetFinderParser @Inject constructor() {
    fun parse(body: String?) : PetFinderJSON? {
        val moshi: Moshi = Moshi.Builder().build()
        val adapter: JsonAdapter<PetFinderJSON> = moshi.adapter(PetFinderJSON::class.java)
        return adapter.fromJson(body)
    }
}