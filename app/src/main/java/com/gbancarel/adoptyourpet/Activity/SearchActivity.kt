package com.gbancarel.adoptyourpet.Activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.setContent
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gbancarel.adoptyourpet.controller.SearchPageControllerDecorator
import com.gbancarel.adoptyourpet.presenter.SearchPageViewModel
import com.gbancarel.adoptyourpet.presenter.data.listBreeds.StateBreedsViewModel
import com.gbancarel.adoptyourpet.state.AnimalSelected
import com.gbancarel.adoptyourpet.ui.FindYourPetTheme
import com.gbancarel.adoptyourpet.ui.page.SearchPage
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import javax.inject.Singleton

@AndroidEntryPoint
class SearchActivity : AppCompatActivity() {

    @Inject lateinit var controller: SearchPageControllerDecorator
    @Inject lateinit var viewModelState: SearchPageViewModel
    var viewModel: AnimalSelectedViewModel = AnimalSelectedViewModel()

    companion object {
        fun newIntent(context: Context) = Intent(context, SearchActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
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
        SearchPage().Page(
            viewModel,
            applicationContext,
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
}

@Singleton
class AnimalSelectedViewModel: ViewModel(), LifecycleObserver {
    val liveData: MutableLiveData<AnimalSelected> = MutableLiveData()
}
