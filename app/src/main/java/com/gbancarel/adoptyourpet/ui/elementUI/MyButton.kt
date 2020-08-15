package com.gbancarel.adoptyourpet.ui.elementUI

import android.content.Context
import android.content.Intent
import androidx.compose.Composable
import androidx.ui.core.Alignment
import androidx.ui.core.ContentScale
import androidx.ui.core.Modifier
import androidx.ui.core.clip
import androidx.ui.foundation.Image
import androidx.ui.foundation.Text
import androidx.ui.foundation.shape.corner.RoundedCornerShape
import androidx.ui.graphics.Color
import androidx.ui.layout.Row
import androidx.ui.layout.fillMaxWidth
import androidx.ui.layout.preferredHeight
import androidx.ui.layout.preferredWidth
import androidx.ui.material.Button
import androidx.ui.material.Divider
import androidx.ui.res.vectorResource
import androidx.ui.unit.dp
import com.gbancarel.adoptyourpet.R
import com.gbancarel.adoptyourpet.ui.typography

class MyButton {

    @Composable
    fun ButtonSearch(intent: Intent, applicationContext: Context) {
        val myImage = vectorResource(id = R.drawable.ic_baseline_search_24)
        val imageModifier = Modifier.preferredHeight(32.dp).preferredWidth(32.dp).clip(shape = RoundedCornerShape(16.dp))

        Button(
                onClick = {applicationContext.startActivity(intent)},
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = Color.White
        ) {
            Row(
                    verticalGravity = Alignment.CenterVertically
            ) {
                Text(
                        text = "Search your pet",
                        style = typography.body2
                )
                Divider(color = Color.Transparent, modifier = Modifier.preferredHeight(7.dp).preferredWidth(7.dp))
                Image(asset = myImage, contentScale =  ContentScale.Crop, modifier = imageModifier)
            }
        }
    }

}