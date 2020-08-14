package com.gbancarel.adoptyourpet.repository.parser

import com.gbancarel.adoptyourpet.repository.json.PetFinderJSON
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import javax.inject.Inject

class PetFinderParser @Inject constructor() {


    fun parse(body: String?) : PetFinderJSON? {
        val moshi = Moshi.Builder()
                .build()
        val adapter: JsonAdapter<PetFinderJSON>? = moshi.adapter(PetFinderJSON::class.java)
        return adapter?.fromJson(body)
    }
}