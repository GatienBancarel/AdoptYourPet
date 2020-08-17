package com.gbancarel.adoptyourpet.Activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.platform.setContent
import androidx.lifecycle.Observer
import com.gbancarel.adoptyourpet.controller.HomePageControllerDecorator
import com.gbancarel.adoptyourpet.presenter.HomePageViewModel
import com.gbancarel.adoptyourpet.presenter.data.PetAnimalViewModel
import com.gbancarel.adoptyourpet.ui.FindYourPetTheme
import com.gbancarel.adoptyourpet.ui.page.HomePage
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject lateinit var controller: HomePageControllerDecorator
    @Inject lateinit var viewModel: HomePageViewModel

    private val petFinderObserver =
        Observer<List<PetAnimalViewModel>> { data -> Log.i("mylog", data.map { it.name }.toString()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.liveData.observe(this, petFinderObserver)

        setContent {
            FindYourPetTheme {
                Surface(color = MaterialTheme.colors.background) {
                    controller.onCreate()
                    HomePage().Page(applicationContext)
                }
            }
        }
    }
}
