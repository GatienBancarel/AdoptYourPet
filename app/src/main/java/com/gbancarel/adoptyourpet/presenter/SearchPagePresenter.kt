package com.gbancarel.adoptyourpet.presenter

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gbancarel.adoptyourpet.presenter.data.listBreeds.StateBreedsViewModel
import javax.inject.Inject
import javax.inject.Singleton

class SearchPagePresenter @Inject constructor(
    val viewModel: SearchPageViewModel
){

    fun present() {
        val stateBreedsViewModel = StateBreedsViewModel.finished
        viewModel.liveData.postValue(stateBreedsViewModel)
    }

    fun presentLoader() {
        val stateBreedsViewModel = StateBreedsViewModel.loading
        viewModel.liveData.postValue(stateBreedsViewModel)
    }

    fun presentError () {
        val stateBreedsViewModel = StateBreedsViewModel.error
        viewModel.liveData.postValue(stateBreedsViewModel)
    }
}

@Singleton
class SearchPageViewModel @Inject constructor(): ViewModel(), LifecycleObserver {
    val liveData: MutableLiveData<StateBreedsViewModel> = MutableLiveData()
}