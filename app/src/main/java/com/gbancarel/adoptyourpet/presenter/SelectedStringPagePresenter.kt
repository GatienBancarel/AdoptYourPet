package com.gbancarel.adoptyourpet.presenter

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gbancarel.adoptyourpet.interactor.data.listBreeds.Breeds
import com.gbancarel.adoptyourpet.interactor.data.listColors.Colors
import com.gbancarel.adoptyourpet.presenter.data.listBreeds.StringSelectedViewModelData
import javax.inject.Inject
import javax.inject.Singleton

class SelectedStringPagePresenter @Inject constructor(
    val viewModel: BreedsPageViewModel,
) {

    fun presentBreeds(listBreeds: List<Breeds>, selectedBreeds: List<String>) {
        val breedsViewModel: List<StringSelectedViewModelData> =
            listBreeds
                .map { breed ->
                    StringSelectedViewModelData(
                        name = breed.name,
                        selected = selectedBreeds.contains(breed.name)
                    )
                }
                .sortedBy { it.name }
        viewModel.liveData.postValue(breedsViewModel)
    }

    fun presentColors(listColors: List<Colors>, selectedColors: List<String>) {
        val colorsViewModel: List<StringSelectedViewModelData> =
            listColors
                .map { color ->
                    StringSelectedViewModelData(
                        name = color.primary,
                        selected = selectedColors.contains(color.primary)
                    )
                }
                .sortedBy { it.name }
        viewModel.liveData.postValue(colorsViewModel)
    }

    fun present(name: String, selected: Boolean) {
        val newList: List<StringSelectedViewModelData>? = viewModel.liveData.value
            ?.filter { it.name != name }
            ?.toMutableList()
            ?.plus(StringSelectedViewModelData(name = name, selected = selected))
            ?.sortedBy { it.name }
        newList?.let {
            viewModel.liveData.postValue(newList)
        }
    }
}

@Singleton
class BreedsPageViewModel @Inject constructor() : ViewModel(), LifecycleObserver {
    val liveData: MutableLiveData<List<StringSelectedViewModelData>> = MutableLiveData()
}