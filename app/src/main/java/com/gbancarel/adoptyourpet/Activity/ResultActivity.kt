package com.gbancarel.adoptyourpet.Activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.gbancarel.adoptyourpet.R
import com.gbancarel.adoptyourpet.controller.ResultPageControllerDecorator
import com.gbancarel.adoptyourpet.presenter.ResultPageViewModel
import com.gbancarel.adoptyourpet.presenter.data.listAnimal.PetFinderViewModelData
import com.gbancarel.adoptyourpet.presenter.data.listAnimal.PetFinderViewModelState
import com.gbancarel.adoptyourpet.ui.FindYourPetTheme
import com.gbancarel.adoptyourpet.ui.customView.CardHomePage
import com.gbancarel.adoptyourpet.ui.typography
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ResultActivity : AppCompatActivity() {

    @Inject
    lateinit var controller: ResultPageControllerDecorator
    @Inject
    lateinit var viewModel: ResultPageViewModel

    companion object {
        fun newIntent(context: Context) = Intent(context, ResultActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FindYourPetTheme {
                Surface(color = MaterialTheme.colors.background) {
                    controller.onCreate(
                        animalSelected = "dog",
                        breedsSelected = "Akita",
                        sizeSelected = "Small",
                        ageSelected = "Young",
                        colorsSelected = "Black",
                        genderSelected = "Male"
                    )
                    val statePetFinderViewModelData = viewModel.liveData.observeAsState(
                        initial = PetFinderViewModelData(
                            animals = emptyList(),
                            state = PetFinderViewModelState.loading,
                            detailAnimal = null
                        )
                    )
                    display(statePetFinderViewModelData)
                }
            }
        }
    }

    @Composable
    fun display(statePetFinderViewModelData: State<PetFinderViewModelData>) {
        when (statePetFinderViewModelData.value.state) {
            PetFinderViewModelState.loading -> {
                AndroidView(
                    viewBlock = { LayoutInflater.from(it).inflate(R.layout.loader, null) },
                    modifier = Modifier.fillMaxSize()
                )
            }
            PetFinderViewModelState.error -> {
                AndroidView(
                    viewBlock = { LayoutInflater.from(it).inflate(R.layout.error, null) },
                    modifier = Modifier.fillMaxSize()
                )
            }
            PetFinderViewModelState.finished -> {
                ResultPage(
                    statePetFinderViewModelData = statePetFinderViewModelData,
                )
            }
        }
    }

    @Composable
    fun ResultPage(statePetFinderViewModelData: State<PetFinderViewModelData>) {
        Surface(
            color = MaterialTheme.colors.background,
            modifier = Modifier.fillMaxWidth().fillMaxHeight()
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Spacer(
                    modifier = Modifier.preferredHeight(16.dp)
                )
                Text(
                    text = "There are ${statePetFinderViewModelData.value.animals.size} result(s):",
                    modifier = Modifier.padding(top = 16.dp,bottom = 16.dp),
                    style = typography.h6
                )
                LazyColumnFor(statePetFinderViewModelData.value.animals) { item ->
                    CardHomePage(
                        name = item.name,
                        description = item.description!!,
                        image = item.photos,
                        shouldShowPhoto = item.shouldShowPhoto,
                        onClick = { }
                    )
                    Spacer(
                        modifier = Modifier.preferredHeight(16.dp)
                    )
                }
            }
        }
    }
}