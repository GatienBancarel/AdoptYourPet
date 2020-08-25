package com.gbancarel.adoptyourpet.Activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.setContent
import androidx.lifecycle.MutableLiveData
import com.gbancarel.adoptyourpet.controller.BreedsPageControllerDecorator
import com.gbancarel.adoptyourpet.presenter.BreedsPageViewModel
import com.gbancarel.adoptyourpet.presenter.data.listBreeds.BreedsViewModel
import com.gbancarel.adoptyourpet.state.AnimalSelected
import com.gbancarel.adoptyourpet.ui.FindYourPetTheme
import com.gbancarel.adoptyourpet.ui.page.BreedsPage
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BreedsActivity : AppCompatActivity() {

    @Inject lateinit var controller: BreedsPageControllerDecorator
    @Inject lateinit var viewModel: BreedsPageViewModel

    companion object {
        fun newIntent(context: Context) = Intent(context, BreedsActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FindYourPetTheme {
                Surface(color = MaterialTheme.colors.background) {
                    Log.i("mylog", "je suis dans l'activity Breeds")
                    controller.onCreate()
                    display(viewModel.liveData)
                }
            }
        }
    }

    @Composable
    fun display(liveData: MutableLiveData<List<BreedsViewModel>>) {
        val data = liveData.observeAsState(
            initial = emptyList()
        )
        BreedsPage().Page(data)
    }
}