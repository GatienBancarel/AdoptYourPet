package com.gbancarel.adoptyourpet.Activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.ui.core.setContent
import androidx.ui.material.MaterialTheme
import androidx.ui.material.Surface
import com.gbancarel.adoptyourpet.controller.MyControllerDecorator
import com.gbancarel.adoptyourpet.presenter.MyViewModel
import com.gbancarel.adoptyourpet.presenter.donnees.PetAnimalViewModel
import com.gbancarel.adoptyourpet.ui.FindYourPetTheme
import com.gbancarel.adoptyourpet.ui.page.HomePage
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject lateinit var controller: MyControllerDecorator
    @Inject lateinit var viewModel: MyViewModel

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
