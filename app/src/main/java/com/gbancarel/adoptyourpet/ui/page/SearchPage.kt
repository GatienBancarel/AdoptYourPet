package com.gbancarel.adoptyourpet.ui.page

import android.content.Context
import androidx.compose.foundation.Box
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.state
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import com.gbancarel.adoptyourpet.Activity.BreedsActivity
import com.gbancarel.adoptyourpet.Activity.SearchPageViewModel
import com.gbancarel.adoptyourpet.state.AnimalSelected
import com.gbancarel.adoptyourpet.ui.customView.AnimalCheckBox
import com.gbancarel.adoptyourpet.ui.typography
import com.gbancarel.adoptyourpet.R
import com.gbancarel.adoptyourpet.controller.BreedsPageControllerDecorator
import com.gbancarel.adoptyourpet.presenter.BreedsPageViewModel
import javax.inject.Inject


class SearchPage {

    @Composable
    fun Page(viewModel: SearchPageViewModel, applicationContext: Context, onAnimalSelected: (AnimalSelected) -> Unit) {
        val step = state { 0 }
        val data = viewModel.liveData.observeAsState(
            initial = AnimalSelected.unknown
        )
        Surface(
            color = MaterialTheme.colors.background,
            modifier = Modifier.fillMaxWidth().fillMaxHeight()
        ) {
            Column (
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalGravity = Alignment.CenterHorizontally
            ) {
                Box(){
                    Text(
                        text = "Tap to choose your pet:",
                        style = typography.h6
                    )
                    Row {
                        AnimalCheckBox(
                            title = "Dog",
                            image = imageResource(id = R.mipmap.dog),
                            isUnknown = data.value == AnimalSelected.unknown,
                            isSelected = data.value == AnimalSelected.dog,
                            onClick = {
                                onAnimalSelected(AnimalSelected.dog)
                                viewModel.liveData.postValue(AnimalSelected.dog)
                                step.value = 1
                            }
                        )
                        Divider(color = Color.Transparent, modifier = Modifier.preferredWidth(4.dp))
                        AnimalCheckBox(
                            title = "Cat",
                            image = imageResource(id = R.mipmap.cat),
                            isUnknown = data.value == AnimalSelected.unknown,
                            isSelected = data.value == AnimalSelected.cat,
                            onClick = {
                                onAnimalSelected(AnimalSelected.cat)
                                viewModel.liveData.postValue(AnimalSelected.cat)
                                step.value = 1
                            }
                        )
                    }
                }
                if (step.value >= 1) {
                    Column(
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .fillMaxWidth(),
                        horizontalGravity = Alignment.CenterHorizontally
                    ) {
                        Button(
                            onClick = {
                                applicationContext.startActivity(
                                    BreedsActivity.newIntent(
                                        applicationContext
                                    )
                                )
                            }
                        ) {
                            Text(
                                text = "Choose my breeds",
                                style = typography.body1
                            )
                        }
                    }
                }
            }
        }
    }
}