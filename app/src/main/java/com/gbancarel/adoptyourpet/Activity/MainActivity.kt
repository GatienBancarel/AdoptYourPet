package com.gbancarel.adoptyourpet.Activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.ui.core.setContent
import androidx.ui.foundation.Text
import androidx.ui.material.MaterialTheme
import androidx.ui.material.Surface
import com.gbancarel.adoptyourpet.controller.MyControllerDecorator
import com.gbancarel.adoptyourpet.presenter.MyViewModel
import com.gbancarel.adoptyourpet.presenter.donnees.PetFinderViewModel
import com.gbancarel.adoptyourpet.ui.FindYourPetTheme
import com.gbancarel.adoptyourpet.ui.page.HomePage
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject lateinit var controller: MyControllerDecorator
    @Inject lateinit var viewModel: MyViewModel

    private val petFinderObserver =
        Observer<PetFinderViewModel> { data -> Log.i("mylog", data?.animals?.get(0)?.colors?.primary.toString()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.liveData.observe(this, petFinderObserver)
        val intent = Intent(applicationContext, SearchActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        setContent {
            FindYourPetTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    controller.onCreate()
                    HomePage().Page(intent, applicationContext)
                    //test(viewModel.liveData)
                }
            }
        }
    }

    @Composable
    fun test(petFinderViewModel: LiveData<PetFinderViewModel>) {
        //val data : State<PetFinderViewModel> = petFinderViewModel.observeAsState(PetFinderViewModel(emptyList()))
        Text(text = "")
    }
}
