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
import com.gbancarel.adoptyourpet.ui.customView.AnimalDetailPage
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
                if (statePetFinderViewModelData.value.detailAnimal != null) {
                    AnimalDetailPage(
                        detailAnimal = statePetFinderViewModelData.value.detailAnimal!!,
                        context = this,
                        onBack = { controller.backClicked() }
                    )
                } else {
                    ResultPage(
                        statePetFinderViewModelData = statePetFinderViewModelData,
                        onClick = { requestedAnimal -> controller.clickedRow(requestedAnimal) }
                    )
                }
            }
        }
    }

    @Composable
    fun ResultPage(statePetFinderViewModelData: State<PetFinderViewModelData>, onClick:(String) -> Unit) {
        Surface(
            color = MaterialTheme.colors.background,
            modifier = Modifier.fillMaxWidth().fillMaxHeight()
        ) {
            Column(
                modifier = Modifier.fillMaxSize().padding(start = 16.dp, end = 16.dp)
            ) {
                Spacer(
                    modifier = Modifier.preferredHeight(16.dp)
                )
                Text(
                    text = "There are ${statePetFinderViewModelData.value.animals.size} result(s):",
                    style = typography.h6
                )
                Spacer(
                    modifier = Modifier.preferredHeight(16.dp)
                )
                LazyColumnFor(statePetFinderViewModelData.value.animals) { item ->
                    CardHomePage(
                        name = item.name,
                        description = item.description!!,
                        image = item.photos,
                        shouldShowPhoto = item.shouldShowPhoto,
                        onClick = {
                            onClick(item.name)
                        }
                    )
                    Spacer(
                        modifier = Modifier.preferredHeight(16.dp)
                    )
                }
            }
        }
    }
}