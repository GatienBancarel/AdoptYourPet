package com.gbancarel.adoptyourpet.ui.elementUI

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.preferredHeight
import androidx.compose.foundation.layout.preferredWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
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