package com.gbancarel.adoptyourpet

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.compose.state
import androidx.lifecycle.Observer
import androidx.ui.core.setContent
import androidx.ui.foundation.Text
import androidx.ui.material.MaterialTheme
import androidx.ui.material.Surface
import androidx.ui.tooling.preview.Preview
import com.gbancarel.adoptyourpet.controller.MyControllerDecorator
import com.gbancarel.adoptyourpet.presenter.MyViewModel
import com.gbancarel.adoptyourpet.presenter.PetFinderViewModel
import com.gbancarel.adoptyourpet.ui.FindYourPetTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject lateinit var controller: MyControllerDecorator
    @Inject lateinit var viewModel: MyViewModel

    private val petFinderObserver =
        Observer<PetFinderViewModel> { data -> Log.i("mylog", data.animals[0].name.toString() )}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("mylog","main activity")
        setContent {
            FindYourPetTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    controller.onCreate()
                    viewModel.liveData.observe(this, petFinderObserver)
                }
            }
        }
    }
}