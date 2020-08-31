package com.gbancarel.adoptyourpet.presenter

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gbancarel.adoptyourpet.interactor.data.Size
import com.gbancarel.adoptyourpet.presenter.data.SearchPageViewModelData
import com.gbancarel.adoptyourpet.presenter.data.listBreeds.StateBreedsViewModel
import com.gbancarel.adoptyourpet.presenter.data.listSize.SizeViewModel
import javax.inject.Inject
import javax.inject.Singleton

class SearchPagePresenter @Inject constructor(
    val viewModel: SearchPageViewModel
) {

    fun presentBreedsLoader() {
        val stateBreedsViewModel = StateBreedsViewModel.loading
        viewModel.liveData.postValue(
            SearchPageViewModelData(
                state = stateBreedsViewModel,
                listOfSize = emptyList(),
                selectedBreeds = emptyList()
            )
        )
    }

    fun presentSizes(sizes: List<Size>) {
        viewModel.liveData.postValue(
            SearchPageViewModelData(
                state = StateBreedsViewModel.loading,
                listOfSize = sizes.map { SizeViewModel(it.id, it.value) },
                selectedBreeds = emptyList()
            )
        )
    }

    fun presentBreedsLoaderFinished() {
        val stateBreedsViewModel = StateBreedsViewModel.finished
        viewModel.liveData.postValue(
            SearchPageViewModelData(
                state = stateBreedsViewModel,
                listOfSize = viewModel.liveData.value?.listOfSize.orEmpty(),
                selectedBreeds = emptyList()
            )
        )
    }

    fun presentError() {
        val stateBreedsViewModel = StateBreedsViewModel.error
        viewModel.liveData.postValue(
            SearchPageViewModelData(
                state = stateBreedsViewModel,
                listOfSize = emptyList(),
                selectedBreeds = emptyList()
            )
        )
    }

    // SELECTION
    fun presentSelectBreeds(breeds: List<String>) = viewModel.liveData.value?.let {
        viewModel.liveData.postValue(
            SearchPageViewModelData(
                state = it.state,
                listOfSize = it.listOfSize,
                selectedBreeds = breeds,
            )
        )
    }

    fun presentSelectedNewSize(id: Int) = viewModel.liveData.value?.let {
            viewModel.liveData.postValue(
                SearchPageViewModelData(
                    state = it.state,
                    listOfSize = it.listOfSize.map { if (it.id == id) it.copy(selected = !it.selected) else it },
                    selectedBreeds = it.selectedBreeds
                )
            )
    }
}

@Singleton
class SearchPageViewModel @Inject constructor() : ViewModel(), LifecycleObserver {
    val liveData: MutableLiveData<SearchPageViewModelData> = MutableLiveData()
}