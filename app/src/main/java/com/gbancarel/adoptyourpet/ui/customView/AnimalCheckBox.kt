package com.gbancarel.adoptyourpet.ui.customView

import androidx.compose.foundation.Box
import androidx.compose.foundation.Image
import androidx.compose.foundation.Text
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.ConstraintLayout
import androidx.compose.foundation.layout.preferredHeight
import androidx.compose.foundation.layout.preferredWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageAsset
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.gbancarel.adoptyourpet.AnimalSelected
import com.gbancarel.adoptyourpet.R
import com.gbancarel.adoptyourpet.ui.typography

@Composable
fun AnimalCheckBox(isSelected: AnimalSelected, onClick: () -> Unit, animal: AnimalSelected) {
    val image: ImageAsset
    val titleBox: String
    if (animal == AnimalSelected.dog ) {
        image = imageResource(id = R.mipmap.dog)
        titleBox = "Dog"
    } else {
        image = imageResource(id = R.mipmap.cat)
        titleBox = "Cat"
    }
    val icon = when (isSelected) {
        AnimalSelected.unknown -> {
            R.drawable.ic_baseline_info_24
        }
        animal  -> {
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
            .clickable(onClick = onClick)
    ) {
        ConstraintLayout {
            val (imageChoose, imageDog,textPet) = createRefs()
            Image(
                asset = image,
                modifier = Modifier
                    .preferredHeight(100.dp)
                    .preferredWidth(100.dp)
                    .constrainAs(imageDog) {
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
                        start.linkTo(imageDog.end)
                        top.linkTo(imageDog.top)
                        end.linkTo(imageDog.end)
                    }
            )
            Text(
                text = titleBox,
                style = typography.body2,
                modifier = Modifier
                    .constrainAs(textPet) {
                        top.linkTo(imageDog.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )
        }
    }
}