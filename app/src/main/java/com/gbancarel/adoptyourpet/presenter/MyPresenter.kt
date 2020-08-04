package com.gbancarel.adoptyourpet.presenter

import android.content.Context
import android.util.Log
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gbancarel.adoptyourpet.interactor.PetFinder
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject
import javax.inject.Singleton

@ActivityScoped
class MyPresenter @Inject constructor(
    val viewModel: MyViewModel,
    @ActivityContext private val context: Context
) {

    fun present(call: PetFinder) {
        val petFinderViewModel = PetFinderViewModel(call.value)
        viewModel.liveData.postValue(petFinderViewModel)
    }

    fun presentError(){
        Log.i("mylog", "Erreur réseau")
    }
}

@Singleton
class MyViewModel @Inject constructor(): ViewModel(), LifecycleObserver {
    val liveData: MutableLiveData<PetFinderViewModel> = MutableLiveData()
}