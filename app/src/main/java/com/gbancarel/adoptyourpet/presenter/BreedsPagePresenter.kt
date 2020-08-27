package com.gbancarel.adoptyourpet.presenter

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gbancarel.adoptyourpet.interactor.data.listBreeds.Breeds
import com.gbancarel.adoptyourpet.presenter.data.listBreeds.BreedsViewModelData
import javax.inject.Inject
import javax.inject.Singleton

class BreedsPagePresenter @Inject constructor(
    val viewModel: BreedsPageViewModel,
) {

    fun present(listBreeds: List<Breeds>, selectedBreeds: List<String>) {
        val breedsViewModel: List<BreedsViewModelData> =
            listBreeds
                .map { breed ->
                    BreedsViewModelData(
                        name = breed.name,
                        selected = selectedBreeds.contains(breed.name)
                    )
                }
                .sortedBy { it.name }
        viewModel.liveData.postValue(breedsViewModel)
    }

    fun present(name: String, selected: Boolean) {
        val newList: List<BreedsViewModelData>? = viewModel.liveData.value
            ?.filter { it.name != name }
            ?.toMutableList()
            ?.plus(BreedsViewModelData(name = name, selected = selected))
            ?.sortedBy { it.name }
        newList?.let { viewModel.liveData.postValue(newList) }
    }
}

@Singleton
class BreedsPageViewModel @Inject constructor() : ViewModel(), LifecycleObserver {
    val liveData: MutableLiveData<List<BreedsViewModelData>> = MutableLiveData()
}