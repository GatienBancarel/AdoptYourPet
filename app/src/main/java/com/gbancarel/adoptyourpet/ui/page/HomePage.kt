package com.gbancarel.adoptyourpet.ui.page

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
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
