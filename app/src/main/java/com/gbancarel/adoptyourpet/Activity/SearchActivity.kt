package com.gbancarel.adoptyourpet.Activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.state
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gbancarel.adoptyourpet.Activity.BreedsActivity.Companion.RESULT_DATA_KEY
import com.gbancarel.adoptyourpet.R
import com.gbancarel.adoptyourpet.controller.SearchPageControllerDecorator
import com.gbancarel.adoptyourpet.presenter.SearchPageViewModel
import com.gbancarel.adoptyourpet.presenter.data.listBreeds.StateBreedsViewModel
import com.gbancarel.adoptyourpet.state.AnimalSelected
import com.gbancarel.adoptyourpet.ui.FindYourPetTheme
import com.gbancarel.adoptyourpet.ui.customView.AnimalCheckBox
import com.gbancarel.adoptyourpet.ui.typography
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import javax.inject.Singleton

@AndroidEntryPoint
class SearchActivity : AppCompatActivity() {

    @Inject lateinit var controller: SearchPageControllerDecorator
    @Inject lateinit var viewModelState: SearchPageViewModel
    var viewModel: AnimalSelectedViewModel = AnimalSelectedViewModel()
    private val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            val list = result.data?.getStringArrayListExtra(RESULT_DATA_KEY)
            Log.i("PBA", "Intent ${list.toString()}")
        }
    }

    companion object {
        fun newIntent(context: Context) = Intent(context, SearchActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FindYourPetTheme {
                Surface(color = MaterialTheme.colors.background) {
                    display(viewModelState.liveData)
                }
            }
        }
    }

    @Composable
    fun display(liveData: MutableLiveData<StateBreedsViewModel>) {
        val dataBreeds = liveData.observeAsState(
            initial = StateBreedsViewModel.loading
        )
        Page(
            viewModel,
            onAnimalSelected = { animalSelected ->
                if (animalSelected == AnimalSelected.dog) {
                    controller.onAnimalCheckboxClicked("dog")
                } else {
                    controller.onAnimalCheckboxClicked("cat")
                }
            },
            dataBreeds
        )
    }

    @Composable
    fun Page(
        viewModel: AnimalSelectedViewModel,
        onAnimalSelected: (AnimalSelected) -> Unit,
        dataBreeds: State<StateBreedsViewModel>
    ) {
        val step = state { 0 }
        val data = viewModel.liveData.observeAsState(
            initial = AnimalSelected.unknown
        )
        Surface(
            color = MaterialTheme.colors.background,
            modifier = Modifier.fillMaxWidth().fillMaxHeight()
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalGravity = Alignment.CenterHorizontally
            ) {
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

                if (step.value >= 1) {
                    Column(
                        modifier = Modifier
                            .padding(top = 32.dp)
                            .fillMaxWidth(),
                        horizontalGravity = Alignment.CenterHorizontally
                    ) {
                        when (dataBreeds.value) {
                            StateBreedsViewModel.loading -> {
                                Text(
                                    text = "Loading...",
                                    style = typography.body2
                                )
                            }
                            StateBreedsViewModel.error -> {
                                Text(
                                    text = "Error: check your Internet connection and retry again.",
                                    style = typography.body2
                                )
                            }
                            StateBreedsViewModel.finished -> {
                                Button(
                                    onClick = {
                                        startForResult.launch(BreedsActivity.newIntent(
                                            applicationContext
                                        ))
                                    }
                                ) {
                                    Text(
                                        text = "Choose yours breeds",
                                        style = typography.body1
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Singleton
class AnimalSelectedViewModel: ViewModel(), LifecycleObserver {
    val liveData: MutableLiveData<AnimalSelected> = MutableLiveData()
}
