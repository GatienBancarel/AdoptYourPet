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

    fun present(listBreeds: List<Breeds>, selectedBreeds: List<String>) {
        val breedsViewModel: List<BreedsViewModel> =
            listBreeds
                .map { breed ->
                    BreedsViewModel(
                        name = breed.name,
                        selected = selectedBreeds.contains(breed.name)
                    )
                }
                .sortedBy { it.name } // TODO GBA T.U
        viewModel.liveData.postValue(breedsViewModel)
    }

    // TODO GBA T.U
    fun present(name: String, selected: Boolean) {
        val newList = viewModel.liveData.value
            ?.filter { it.name != name }
            ?.toMutableList()
            ?.plus(BreedsViewModel(name = name, selected = selected))
            ?.sortedBy { it.name }
        viewModel.liveData.postValue(newList)
    }
}

@Singleton
class BreedsPageViewModel @Inject constructor() : ViewModel(), LifecycleObserver {
    val liveData: MutableLiveData<List<BreedsViewModel>> = MutableLiveData()
}