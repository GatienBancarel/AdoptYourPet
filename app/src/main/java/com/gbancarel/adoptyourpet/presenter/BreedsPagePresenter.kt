package com.gbancarel.adoptyourpet.presenter

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gbancarel.adoptyourpet.presenter.data.listBreeds.BreedsViewModel
import javax.inject.Inject
import javax.inject.Singleton

class BreedsPagePresenter @Inject constructor(
    val viewModel: BreedsPageViewModel,
) {

    fun present(listBreeds: List<BreedLocalInteractor>) {
        val breedsViewModel: List<BreedsViewModel> =
            listBreeds.map { BreedLocalInteractor ->
                BreedsViewModel(
                    name = BreedLocalInteractor.name
                )
            }
        viewModel.liveData.postValue(breedsViewModel)
    }
}

@Singleton
class BreedsPageViewModel @Inject constructor(): ViewModel(), LifecycleObserver {
    val liveData: MutableLiveData<List<BreedsViewModel>> = MutableLiveData()
}