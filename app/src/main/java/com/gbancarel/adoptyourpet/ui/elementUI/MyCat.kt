package com.gbancarel.adoptyourpet.ui.elementUI

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.ConstraintLayout
import androidx.compose.foundation.layout.preferredHeight
import androidx.compose.foundation.layout.preferredWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.gbancarel.adoptyourpet.Activity.SearchPageViewModel
import com.gbancarel.adoptyourpet.R
import com.gbancarel.adoptyourpet.StateSearchActivity
import com.gbancarel.adoptyourpet.ui.typography

@Composable
fun MyCat(type: String, viewModel: SearchPageViewModel) {
    val icon = when (type) {
        "unknown" -> {
            R.drawable.ic_baseline_info_24
        }
        "cat" -> {
            R.drawable.ic_baseline_check_24
        }
        else -> {
            R.drawable.ic_baseline_close_24
        }
    }
    Box(
        modifier = Modifier
            .preferredWidth(125.dp)
            .preferredHeight(125.dp)
            .clickable(onClick = {viewModel.liveData.postValue(StateSearchActivity(type = "cat"))})
    ) {
        ConstraintLayout {
            val (imageChoose, imageCat,textPet) = createRefs()
            Image(
                asset = imageResource(id = R.mipmap.cat),
                modifier = Modifier
                    .preferredHeight(100.dp)
                    .preferredWidth(100.dp)
                    .constrainAs(imageCat) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                    }
            )
            Image(
                asset = vectorResource(id = icon),
                modifier = Modifier
                    .preferredHeight(25.dp)
                    .preferredWidth(25.dp)
                    .constrainAs(imageChoose) {
                        start.linkTo(imageCat.end)
                        top.linkTo(imageCat.top)
                        end.linkTo(imageCat.end)
                    }
            )
            Text(
                text = "Cat",
                style = typography.body2,
                modifier = Modifier
                    .constrainAs(textPet) {
                        top.linkTo(imageCat.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )
        }
    }
}