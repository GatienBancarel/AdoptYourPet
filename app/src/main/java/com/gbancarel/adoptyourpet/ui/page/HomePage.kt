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
import com.gbancarel.adoptyourpet.ui.elementUI.MyButton

class HomePage {

    @Composable
    fun Page() {

        Surface(
                color = MaterialTheme.colors.background,
                modifier = Modifier.fillMaxWidth().fillMaxHeight()
        ) {
            Column(
                    modifier = Modifier.padding(16.dp)
            ) {
                MyButton().ButtonSearch()
                Divider(color = Color.Transparent, modifier = Modifier.preferredHeight(32.dp))
            }
        }
    }
}
