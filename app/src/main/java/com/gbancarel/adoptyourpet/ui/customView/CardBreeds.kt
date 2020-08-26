package com.gbancarel.adoptyourpet.ui.customView

import androidx.compose.foundation.Box
import androidx.compose.foundation.Text
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Checkbox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.state
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.gbancarel.adoptyourpet.ui.typography

@Composable
fun CardBreeds(
    breed: String,
    onCheckedChange: (String, Boolean) -> Unit
) {
    val checkedState = remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .preferredHeight(35.dp)
            .clickable(onClick = {
                checkedState.value = !checkedState.value
                onCheckedChange(breed, checkedState.value)
            })
    ) {

        ConstraintLayout(
            modifier = Modifier.fillMaxWidth().fillMaxHeight()
        ) {
            val (textBreed, checkBox) = createRefs()
            Text(
                text = breed,
                style = typography.body1,
                modifier = Modifier
                    .constrainAs(textBreed) {
                        start.linkTo(parent.start, margin = 2.dp)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
            )
            Checkbox(
                checked = checkedState.value,
                onCheckedChange = {
                    checkedState.value = it
                    onCheckedChange(breed, it)
                },
                modifier = Modifier
                    .constrainAs(checkBox) {
                        end.linkTo(parent.end, margin = 8.dp)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
            )
        }
    }
}