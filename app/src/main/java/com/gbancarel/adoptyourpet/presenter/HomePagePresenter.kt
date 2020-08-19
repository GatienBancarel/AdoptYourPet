package com.gbancarel.adoptyourpet.presenter

import android.content.Context
import android.util.Log
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gbancarel.adoptyourpet.interactor.data.*
import com.gbancarel.adoptyourpet.presenter.data.*
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject
import javax.inject.Singleton

@ActivityScoped
class HomePagePresenter @Inject constructor(
    val viewModel: HomePageViewModel,
    @ActivityContext private val context: Context
) {

    fun present(listAnimal: List<PetAnimal>) {
        val petFinderViewModel = PetFinderViewModel(
            error = false,
            loader = false,
            animals = listAnimal.map { PetAnimal ->
                PetAnimalViewModel(
                    name = PetAnimal.name,
                    description = PetAnimal.description ?: "No description",
                    photos = PetAnimal.photos.map { Photo ->
                        PhotoViewModel(small = Photo.small)
                    },
                    shouldShowPhoto = PetAnimal.photos.isNotEmpty()
                )
            }
        )
        viewModel.liveData.postValue(petFinderViewModel)
    }

    fun presentLoader() {
        val petFinderViewModel = PetFinderViewModel(
            error = false,
            loader = true,
            animals = emptyList()
        )
        viewModel.liveData.postValue(petFinderViewModel)
    }

    fun presentErrorOkHttp(){
        val petFinderViewModel = PetFinderViewModel(
            error = true,
            loader = false,
            animals = emptyList()
        )
        viewModel.liveData.postValue(petFinderViewModel)
    }

    fun presentErrorMoshi(){
        val petFinderViewModel = PetFinderViewModel(
            error = true,
            loader = false,
            animals = emptyList()
        )
        viewModel.liveData.postValue(petFinderViewModel)
    }

}

@Singleton
class HomePageViewModel @Inject constructor(): ViewModel(), LifecycleObserver {
    val liveData: MutableLiveData<PetFinderViewModel> = MutableLiveData()
}