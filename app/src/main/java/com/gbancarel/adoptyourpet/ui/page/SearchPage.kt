package com.gbancarel.adoptyourpet.ui.page

import androidx.compose.foundation.Box
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import com.gbancarel.adoptyourpet.Activity.SearchPageViewModel
import com.gbancarel.adoptyourpet.AnimalSelected
import com.gbancarel.adoptyourpet.ui.customView.AnimalCheckBox
import com.gbancarel.adoptyourpet.ui.typography
import com.gbancarel.adoptyourpet.R


class SearchPage {

    @Composable
    fun Page(viewModel: SearchPageViewModel) {
        val data = viewModel.liveData.observeAsState(
            initial = AnimalSelected.unknown
        )
        Surface(
            color = MaterialTheme.colors.background,
            modifier = Modifier.fillMaxWidth().fillMaxHeight()
        ) {
            Column (
                modifier = Modifier
                    .padding(16.dp),
                horizontalGravity = Alignment.CenterHorizontally
            ) {
                Box(){
                    Text(
                        text = "Tap to choose your pet:",
                        style = typography.body2
                    )
                    Row {
                        AnimalCheckBox(
                            title = "Dog",
                            image = imageResource(id = R.mipmap.dog),
                            isUnknown = data.value == AnimalSelected.unknown,
                            isSelected = data.value == AnimalSelected.dog,
                            onClick = { viewModel.liveData.postValue(AnimalSelected.dog) }
                        )
                        AnimalCheckBox(
                            title = "Cat",
                            image = imageResource(id = R.mipmap.cat),
                            isUnknown = data.value == AnimalSelected.unknown,
                            isSelected = data.value == AnimalSelected.cat,
                            onClick = { viewModel.liveData.postValue(AnimalSelected.cat) }
                        )
                    }
                }
            }
        }
    }
}