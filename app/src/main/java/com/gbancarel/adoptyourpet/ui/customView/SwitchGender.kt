package com.gbancarel.adoptyourpet.ui.customView

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Box
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import com.gbancarel.adoptyourpet.R
import com.gbancarel.adoptyourpet.ui.lightBlue200
import com.gbancarel.adoptyourpet.ui.lightBlue50
import com.gbancarel.adoptyourpet.ui.pinkA100

@Composable
fun SwitchGender(onClick: () -> Unit, genderSelected: String) {
    Crossfade(current = genderSelected) { screen ->
        when (screen) {
            "male" -> SwitchStateMale(onClick)
            "female" -> SwitchStateFemale(onClick)
        }
    }
}

@Composable
fun SwitchStateMale(onClick: () -> Unit) {
    Card(
        modifier = Modifier.preferredHeight(50.dp).preferredWidth(200.dp)
    ) {
        Row {
            Box(
                modifier = Modifier.preferredHeight(50.dp).preferredWidth(100.dp)
                    .background(color = lightBlue200)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalGravity = Alignment.CenterHorizontally
                ) {
                    Spacer(
                        modifier = Modifier.preferredHeight(3.dp)
                    )
                    Image(
                        asset = imageResource(id = R.drawable.male),
                        modifier = Modifier.preferredHeight(44.dp).preferredWidth(100.dp)
                    )
                    Spacer(
                        modifier = Modifier.preferredHeight(3.dp)
                    )
                }
            }
            Box(
                modifier = Modifier
                    .preferredHeight(50.dp)
                    .preferredWidth(100.dp)
                    .clickable(onClick = onClick)
                    .background(color = lightBlue50)
            ) { }
        }
    }
}

@Composable
fun SwitchStateFemale(onClick: () -> Unit) {
    Card(
        modifier = Modifier.preferredHeight(50.dp).preferredWidth(200.dp)
    ) {
        Row {
            Box(
                modifier = Modifier
                    .preferredHeight(50.dp)
                    .preferredWidth(100.dp)
                    .clickable(onClick = onClick)
                    .background(color = lightBlue50)
            ) { }
            Box(
                modifier = Modifier
                    .preferredHeight(50.dp)
                    .preferredWidth(100.dp)
                    .background(color = pinkA100)
                    .background(color = lightBlue200)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalGravity = Alignment.CenterHorizontally
                ) {
                    Spacer(
                        modifier = Modifier.preferredHeight(3.dp)
                    )
                    Image(
                        asset = imageResource(id = R.drawable.female),
                        modifier = Modifier.preferredHeight(44.dp).preferredWidth(100.dp)
                    )
                    Spacer(
                        modifier = Modifier.preferredHeight(3.dp)
                    )
                }
            }
        }
    }
}

