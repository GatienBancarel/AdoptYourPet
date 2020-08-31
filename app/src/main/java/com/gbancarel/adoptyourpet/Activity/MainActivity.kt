package com.gbancarel.adoptyourpet.Activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.gbancarel.adoptyourpet.controller.HomePageControllerDecorator
import com.gbancarel.adoptyourpet.presenter.HomePageViewModel
import com.gbancarel.adoptyourpet.presenter.data.listAnimal.PetFinderViewModelData
import com.gbancarel.adoptyourpet.presenter.data.listAnimal.PetFinderViewModelState
import com.gbancarel.adoptyourpet.ui.FindYourPetTheme
import com.gbancarel.adoptyourpet.ui.customView.AnimalDetailPage
import com.gbancarel.adoptyourpet.ui.customView.ButtonSearch
import com.gbancarel.adoptyourpet.ui.customView.CardHomePage
import com.gbancarel.adoptyourpet.R
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var controller: HomePageControllerDecorator
    @Inject
    lateinit var viewModel: HomePageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FindYourPetTheme {
                Surface(color = MaterialTheme.colors.background) {
                    controller.onCreate()
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
                    AndroidView(viewBlock = { LayoutInflater.from(it).inflate(R.layout.loader, null)}, modifier = Modifier.fillMaxSize())
                }
                PetFinderViewModelState.error -> {
                    AndroidView(viewBlock = { LayoutInflater.from(it).inflate(R.layout.error, null) }, modifier = Modifier.fillMaxSize())
                }
                PetFinderViewModelState.finished -> {
                    if (statePetFinderViewModelData.value.detailAnimal != null) {
                        AnimalDetailPage(
                            detailAnimal = statePetFinderViewModelData.value.detailAnimal!!,
                            onBack = { controller.backClicked() }
                        )
                    } else {
                        HomePage(
                            statePetFinderViewModelData = statePetFinderViewModelData,
                            onClick = { requestedAnimal -> controller.clickedRow(requestedAnimal) }
                        )
                    }
                }
        }
    }

    @Composable
    fun HomePage(
        statePetFinderViewModelData: State<PetFinderViewModelData>,
        onClick: (String) -> Unit
    ) {

        Surface(
            color = MaterialTheme.colors.background,
            modifier = Modifier.fillMaxWidth().fillMaxHeight()
        ) {
            Column(
                modifier = Modifier.padding(16.dp).fillMaxSize()
            ) {
                ButtonSearch(
                    onClick = {
                        startActivity(
                            SearchActivity.newIntent(
                                applicationContext
                            )
                        )
                    }
                )
                Divider(
                    color = Color.Transparent,
                    modifier = Modifier.preferredHeight(32.dp)
                )
                LazyColumnFor(statePetFinderViewModelData.value.animals) { item ->
                    CardHomePage(
                        name = item.name,
                        description = item.description!!,
                        image = item.photos,
                        shouldShowPhoto = item.shouldShowPhoto,
                        onClick = { onClick(item.name) }
                    )
                    Divider(
                        color = Color.Transparent,
                        modifier = Modifier.preferredHeight(16.dp)
                    )
                }
            }
        }
    }

    fun retry(view: View) {
        controller.onCreate()
    }

    override fun onBackPressed() {
        if (viewModel.liveData.value?.detailAnimal != null) {
            controller.backClicked()
        } else {
            super.onBackPressed()
        }
    }
}
