package com.gbancarel.adoptyourpet.Activity

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.setContent
import androidx.lifecycle.MutableLiveData
import com.gbancarel.adoptyourpet.controller.HomePageControllerDecorator
import com.gbancarel.adoptyourpet.databinding.ErrorBinding
import com.gbancarel.adoptyourpet.databinding.LoaderBinding
import com.gbancarel.adoptyourpet.presenter.HomePageViewModel
import com.gbancarel.adoptyourpet.presenter.data.listAnimal.PetFinderViewModel
import com.gbancarel.adoptyourpet.presenter.data.listAnimal.PetFinderViewModelState
import com.gbancarel.adoptyourpet.ui.FindYourPetTheme
import com.gbancarel.adoptyourpet.ui.page.HomePage
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject lateinit var controller: HomePageControllerDecorator
    @Inject lateinit var viewModel: HomePageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FindYourPetTheme {
                Surface(color = MaterialTheme.colors.background) {
                    Log.i("mylog", "je suis dans l'activity")
                    controller.onCreate()
                    display(viewModel.liveData)
                }
            }
        }
    }

    @Composable
    fun display(liveData: MutableLiveData<PetFinderViewModel>) {
        val data = liveData.observeAsState(
            initial = PetFinderViewModel(
                animals = emptyList(),
                state = PetFinderViewModelState.loading
            )
        )
        when (data.value.state) {
            PetFinderViewModelState.loading -> {
                val bindingLoader = LoaderBinding.inflate(layoutInflater)
                setContentView(bindingLoader.root)
            }
            PetFinderViewModelState.error -> {
                val bindingError = ErrorBinding.inflate(layoutInflater)
                setContentView(bindingError.root)
            }
            PetFinderViewModelState.finished -> {
                setContent{
                    HomePage().Page(applicationContext,data)
                }
            }
        }
    }

    fun retry(view: View) {
        controller.onCreate()
    }
}
