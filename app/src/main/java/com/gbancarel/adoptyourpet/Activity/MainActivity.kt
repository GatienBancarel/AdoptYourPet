package com.gbancarel.adoptyourpet.Activity

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
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
import com.gbancarel.adoptyourpet.presenter.data.PetFinderViewModel
import com.gbancarel.adoptyourpet.ui.FindYourPetTheme
import com.gbancarel.adoptyourpet.ui.page.HomePage
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject lateinit var controller: HomePageControllerDecorator
    @Inject lateinit var viewModel: HomePageViewModel

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FindYourPetTheme {
                Surface(color = MaterialTheme.colors.background) {
                    controller.onCreate()
                    display(viewModel.liveData)
                }
            }
        }
    }

    @Composable
    fun display(liveData: MutableLiveData<PetFinderViewModel>) {
        val data = liveData.observeAsState(initial = PetFinderViewModel(emptyList()))
        when {
            data.value.loader -> {
                val bindingLoader = LoaderBinding.inflate(layoutInflater)
                setContentView(bindingLoader.root)
            }
            data.value.error -> {
                val bindingError = ErrorBinding.inflate(layoutInflater)
                setContentView(bindingError.root)
            }
            else -> {
                setContent{
                    HomePage().Page(applicationContext,data)
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun retry(view: View) {
        controller.onCreate()
    }
}
