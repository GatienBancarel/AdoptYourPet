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

    fun present() {
        val stateBreedsViewModel = StateBreedsViewModel.finished
        viewModel.liveData.postValue(
            SearchPageViewModelData(
                state = stateBreedsViewModel,
                selectedBreeds = emptyList(),
                selectedSize = viewModel.liveData.value!!.selectedSize
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

    fun present(breeds: List<String>) {
        val stateBreedsViewModel = StateBreedsViewModel.finished
        viewModel.liveData.postValue(
            SearchPageViewModelData(
                state = stateBreedsViewModel,
                selectedBreeds = breeds,
                selectedSize = viewModel.liveData.value!!.selectedSize
            )
        )
    }

    fun presentSize(size: String, selected: Boolean, order: Int) {
        val newList: List<SizeViewModel>? = viewModel.liveData.value?.selectedSize
            ?.filter { it.name != size }
            ?.toMutableList()
            ?.plus(SizeViewModel(name = size, selected = selected, order = order))
            ?.sortedBy { it.order }
        newList?.let {
            viewModel.liveData.postValue(
                SearchPageViewModelData(
                    state = viewModel.liveData.value!!.state,
                    selectedBreeds = viewModel.liveData.value!!.selectedBreeds,
                    selectedSize = newList
                )
            )
        }
    }
}

@Singleton
class SearchPageViewModel @Inject constructor() : ViewModel(), LifecycleObserver {
    val liveData: MutableLiveData<SearchPageViewModelData> = MutableLiveData()
}