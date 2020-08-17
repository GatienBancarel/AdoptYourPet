package com.gbancarel.adoptyourpet.repository.parser

import com.gbancarel.adoptyourpet.repository.json.TokenJSON
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import javax.inject.Inject

class TokenParser @Inject constructor() {
    fun parse(body: String?) : TokenJSON? {
        val moshi: Moshi = Moshi.Builder().build()
        val adapter: JsonAdapter<TokenJSON>? = moshi.adapter(TokenJSON::class.java)
        return adapter?.fromJson(body)
    }
}