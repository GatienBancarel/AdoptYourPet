package com.gbancarel.adoptyourpet.Activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class SearchActivity : AppCompatActivity() {

    companion object {
        fun newIntent(context: Context) = Intent(context, SearchActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}