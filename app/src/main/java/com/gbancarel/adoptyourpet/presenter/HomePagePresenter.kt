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

    fun present(call: List<PetAnimal>) {
        val petFinderViewModel = call.map { PetAnimal ->
            PetAnimalViewModel(
                    name = PetAnimal.name,
                    description= PetAnimal.description,
                    photos = PetAnimal.photos.map { Photo ->
                        PhotoViewModel(small = Photo.small)
                    },
                    shouldShowPhoto = PetAnimal.photos.isNotEmpty()
            )
        }
        viewModel.liveData.postValue(petFinderViewModel)
    }

    fun presentErrorOkHttp(){
        Log.i("mylog", "okhttp failed")
    }

    fun presentErrorMoshi(){
        Log.i("mylog", "moshi failed")
    }

}

@Singleton
class HomePageViewModel @Inject constructor(): ViewModel(), LifecycleObserver {
    val liveData: MutableLiveData<List<PetAnimalViewModel>> = MutableLiveData()
}