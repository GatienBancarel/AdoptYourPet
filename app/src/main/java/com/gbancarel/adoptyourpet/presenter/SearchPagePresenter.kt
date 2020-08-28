package com.gbancarel.adoptyourpet.presenter

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gbancarel.adoptyourpet.presenter.data.SearchPageViewModelData
import com.gbancarel.adoptyourpet.presenter.data.listBreeds.StateBreedsViewModel
import com.gbancarel.adoptyourpet.presenter.data.listSize.SizeViewModel
import javax.inject.Inject
import javax.inject.Singleton

class SearchPagePresenter @Inject constructor(
    val viewModel: SearchPageViewModel
) {

    fun present() = viewModel.liveData.value?.let {
        viewModel.liveData.postValue(
            SearchPageViewModelData(
                state = StateBreedsViewModel.finished,
                selectedBreeds = emptyList(),
                selectedSize = it.selectedSize
            )
        )
    }

    fun presentLoader() {
        val stateBreedsViewModel = StateBreedsViewModel.loading
        viewModel.liveData.postValue(
            SearchPageViewModelData(
                state = stateBreedsViewModel,
                selectedBreeds = emptyList()
            )
        )
    }

    fun presentError() {
        val stateBreedsViewModel = StateBreedsViewModel.error
        viewModel.liveData.postValue(
            SearchPageViewModelData(
                state = stateBreedsViewModel,
                selectedBreeds = emptyList()
            )
        )
    }

    fun present(breeds: List<String>) = viewModel.liveData.value?.let {
        viewModel.liveData.postValue(
            SearchPageViewModelData(
                state = StateBreedsViewModel.finished,
                selectedBreeds = breeds,
                selectedSize = it.selectedSize
            )
        )
    }

    fun presentSize(size: String, selected: Boolean, order: Int) = viewModel.liveData.value?.let {
        val newList = it.selectedSize
            .filter { it.name != size }
            .toMutableList()
            .plus(SizeViewModel(name = size, selected = selected, order = order))
            .sortedBy { it.order }

            viewModel.liveData.postValue(
                SearchPageViewModelData(
                    state = it.state,
                    selectedBreeds = it.selectedBreeds,
                    selectedSize = newList
                )
            )
    }
}

@Singleton
class SearchPageViewModel @Inject constructor() : ViewModel(), LifecycleObserver {
    val liveData: MutableLiveData<SearchPageViewModelData> = MutableLiveData()
}