package com.gbancarel.adoptyourpet.repository.parser

import com.gbancarel.adoptyourpet.repository.json.listColors.TypeJSON
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import javax.inject.Inject

class ColorsParser @Inject constructor() {

    fun parse(body: String?) : TypeJSON? {
        val moshi = Moshi.Builder()
            .build()
        val adapter: JsonAdapter<TypeJSON>? = moshi.adapter(TypeJSON::class.java)
        return adapter?.fromJson(body)
    }
}