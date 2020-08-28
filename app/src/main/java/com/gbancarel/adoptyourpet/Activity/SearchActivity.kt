package com.gbancarel.adoptyourpet.Activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gbancarel.adoptyourpet.Activity.BreedsActivity.Companion.RESULT_DATA_KEY
import com.gbancarel.adoptyourpet.R
import com.gbancarel.adoptyourpet.controller.SearchPageControllerDecorator
import com.gbancarel.adoptyourpet.presenter.SearchPageViewModel
import com.gbancarel.adoptyourpet.presenter.data.SearchPageViewModelData
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
    private var viewModel: AnimalSelectedViewModel = AnimalSelectedViewModel()
    private val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.getStringArrayListExtra(RESULT_DATA_KEY)?.let { list ->
                    controller.onSelectedBreeds(list.toList())
                }
            }
        }

    companion object {
        fun newIntent(context: Context) = Intent(context, SearchActivity::class.java)
    }

    @ExperimentalLayout
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

    @ExperimentalLayout
    @Composable
    fun display(liveData: MutableLiveData<SearchPageViewModelData>) {
        val searchViewModelData = liveData.observeAsState(
            initial = SearchPageViewModelData(StateBreedsViewModel.loading, emptyList())
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
            searchViewModelData
        )
    }

    @ExperimentalLayout
    @Composable
    fun Page(
        viewModel: AnimalSelectedViewModel,
        onAnimalSelected: (AnimalSelected) -> Unit,
        searchViewModelData: State<SearchPageViewModelData>
    ) {
        var step = remember { mutableStateOf(0) }
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
                    Text(
                        text = "Tap to choose your Breeds:",
                        style = typography.h6,
                        modifier = Modifier.padding(top = 32.dp, bottom = 16.dp)
                    )
                    when (searchViewModelData.value.state) {
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
                                    startForResult.launch(
                                        BreedsActivity.newIntent(
                                            applicationContext,
                                            searchViewModelData.value.selectedBreeds
                                        )
                                    )
                                    step.value = 2
                                }
                            ) {
                                Text(
                                    text = "Choose yours breeds",
                                    style = typography.body1
                                )
                            }
                        }
                    }
                    val infoSize =
                        if (data.value == AnimalSelected.dog) {
                            listOf<String>(
                                "(0-25 lbs)",
                                "(26-60 lbs)",
                                "(61-100 lbs)",
                                "(101 lbs or more)"
                            )
                        } else {
                            listOf<String>(
                                "(0-6 lbs)",
                                "(7-11 lbs)",
                                "(12-16 lbs)",
                                "(17 lbs or more)"
                            )
                        }
                    Text(
                        text = "Tap to choose your Size:",
                        style = typography.h6,
                        modifier = Modifier.padding(top = 32.dp, bottom = 16.dp)
                    )
                    searchViewModelData.value.selectedSize.forEachIndexed { index, listSize ->
                        Button(
                            onClick = { controller.onSelectedSize(listSize.name, !listSize.selected, listSize.order) },
                            shape = RoundedCornerShape(15.dp),
                            modifier = Modifier.padding(4.dp)
                        ) {
                            Text(
                                text = if (listSize.selected) "✓ ${listSize.name}" + infoSize[index] else listSize.name + infoSize[index],
                                style = typography.body1,
                                overflow = TextOverflow.Ellipsis,
                            )
                        }
                    }
                }
            }
        }
    }
}


@Singleton
class AnimalSelectedViewModel : ViewModel(), LifecycleObserver {
    val liveData: MutableLiveData<AnimalSelected> = MutableLiveData()
}
