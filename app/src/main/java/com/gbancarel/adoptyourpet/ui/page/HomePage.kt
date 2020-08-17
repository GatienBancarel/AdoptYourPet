package com.gbancarel.adoptyourpet.ui.page

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.gbancarel.adoptyourpet.ui.elementUI.MyButton

class HomePage {

    @Composable
    fun Page(intent: Intent, applicationContext: Context) {

        Surface(
                color = MaterialTheme.colors.background,
                modifier = Modifier.fillMaxWidth().fillMaxHeight()
        ) {
            Column(
                    modifier = Modifier.padding(16.dp)
            ) {
                MyButton().ButtonSearch(intent,applicationContext)
                Divider(color = Color.Transparent, modifier = Modifier.preferredHeight(32.dp))
            }
        }
    }
}
