package com.gbancarel.adoptyourpet.presenter

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gbancarel.adoptyourpet.interactor.data.listBreeds.Breeds
import com.gbancarel.adoptyourpet.presenter.data.listBreeds.BreedsViewModel
import javax.inject.Inject
import javax.inject.Singleton

class BreedsPagePresenter @Inject constructor(
    val viewModel: BreedsPageViewModel,
) {

    fun present(listBreeds: List<Breeds>) {
        val breedsViewModel: List<BreedsViewModel> =
            listBreeds.map { Breeds ->
                BreedsViewModel(
                    name = Breeds.name
                )
            }
        viewModel.liveData.postValue(breedsViewModel)
    }
}

@Singleton
class BreedsPageViewModel @Inject constructor(): ViewModel(), LifecycleObserver {
    val liveData: MutableLiveData<List<BreedsViewModel>> = MutableLiveData()
}