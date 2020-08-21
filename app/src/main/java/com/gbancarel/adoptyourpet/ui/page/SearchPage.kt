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
import androidx.compose.ui.unit.dp
import com.gbancarel.adoptyourpet.Activity.SearchPageViewModel
import com.gbancarel.adoptyourpet.AnimalSelected
import com.gbancarel.adoptyourpet.ui.customView.AnimalCheckBox
import com.gbancarel.adoptyourpet.ui.typography


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
                            animal = AnimalSelected.dog,
                            isSelected = data.value,
                            onClick = { viewModel.liveData.postValue(AnimalSelected.dog) }
                        )
                        AnimalCheckBox(
                            animal = AnimalSelected.cat,
                            isSelected = data.value,
                            onClick = { viewModel.liveData.postValue(AnimalSelected.cat) },
                        )
                    }
                }
            }
        }
    }
}