package com.gbancarel.adoptyourpet.Activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
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
import androidx.lifecycle.MutableLiveData
import com.gbancarel.adoptyourpet.controller.HomePageControllerDecorator
import com.gbancarel.adoptyourpet.databinding.ErrorBinding
import com.gbancarel.adoptyourpet.databinding.LoaderBinding
import com.gbancarel.adoptyourpet.presenter.HomePageViewModel
import com.gbancarel.adoptyourpet.presenter.data.listAnimal.PetFinderViewModel
import com.gbancarel.adoptyourpet.presenter.data.listAnimal.PetFinderViewModelState
import com.gbancarel.adoptyourpet.ui.FindYourPetTheme
import com.gbancarel.adoptyourpet.ui.customView.ButtonSearch
import com.gbancarel.adoptyourpet.ui.customView.CardHomePage
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
                    Page(data)
                }
            }
        }
    }

    @Composable
    fun Page(
        liveData: State<PetFinderViewModel>
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
                        applicationContext.startActivity(
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
                LazyColumnFor(liveData.value.animals) { item ->
                    CardHomePage(
                        name = item.name,
                        description = item.description!!,
                        image = item.photos,
                        shouldShowPhoto = item.shouldShowPhoto
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
}
