package com.gbancarel.adoptyourpet.ui.page

import android.content.Context
import android.content.Intent
import androidx.compose.Composable
import androidx.ui.core.Modifier
import androidx.ui.graphics.Color
import androidx.ui.layout.*
import androidx.ui.material.Divider
import androidx.ui.material.MaterialTheme
import androidx.ui.material.Surface
import androidx.ui.unit.dp
import com.gbancarel.adoptyourpet.Activity.SearchActivity
import com.gbancarel.adoptyourpet.ui.elementUI.ButtonSearch

class HomePage {

    @Composable
    fun Page(applicationContext: Context) {

        Surface(
                color = MaterialTheme.colors.background,
                modifier = Modifier.fillMaxWidth().fillMaxHeight()
        ) {
            Column(
                    modifier = Modifier.padding(16.dp)
            ) {
                ButtonSearch(
                    onClick = { applicationContext.startActivity(SearchActivity.newIntent(applicationContext)) }
                )
                Divider(
                    color = Color.Transparent,
                    modifier = Modifier.preferredHeight(32.dp)
                )
            }
        }
    }
}
