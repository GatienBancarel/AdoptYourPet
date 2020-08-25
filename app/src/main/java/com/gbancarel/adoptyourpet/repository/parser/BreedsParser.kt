package com.gbancarel.adoptyourpet.repository.parser

import com.gbancarel.adoptyourpet.repository.json.listBreeds.ListBreedsJSON
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import javax.inject.Inject

class BreedsParser @Inject constructor() {


    fun parse(body: String?) : ListBreedsJSON? {
        val moshi = Moshi.Builder()
            .build()
        val adapter: JsonAdapter<ListBreedsJSON>? = moshi.adapter(ListBreedsJSON::class.java)
        return adapter?.fromJson(body)
    }
}