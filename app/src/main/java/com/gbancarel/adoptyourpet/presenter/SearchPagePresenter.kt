package com.gbancarel.adoptyourpet.presenter

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gbancarel.adoptyourpet.interactor.data.Age
import com.gbancarel.adoptyourpet.interactor.data.Size
import com.gbancarel.adoptyourpet.presenter.data.SearchPageViewModelData
import com.gbancarel.adoptyourpet.presenter.data.listAge.AgeViewModel
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
                selectedBreeds = emptyList(),
                selectedAge = emptyList()
            )
        )
    }

    fun present(sizes: List<Size>, ages: List<Age>) {
        viewModel.liveData.postValue(
            SearchPageViewModelData(
                state = StateBreedsViewModel.loading,
                listOfSize = sizes.map { SizeViewModel(it.id, it.value) },
                selectedBreeds = emptyList(),
                selectedAge = ages.map { AgeViewModel(it.id, it.value, false) },
            )
        )
    }

    fun presentBreedsLoaderFinished() {
        val stateBreedsViewModel = StateBreedsViewModel.finished
        viewModel.liveData.postValue(
            SearchPageViewModelData(
                state = stateBreedsViewModel,
                listOfSize = viewModel.liveData.value?.listOfSize.orEmpty(),
                selectedBreeds = emptyList(),
                selectedAge = viewModel.liveData.value?.selectedAge.orEmpty(),
            )
        )
    }

    fun presentError() {
        val stateBreedsViewModel = StateBreedsViewModel.error
        viewModel.liveData.postValue(
            SearchPageViewModelData(
                state = stateBreedsViewModel,
                listOfSize = emptyList(),
                selectedBreeds = emptyList(),
                selectedAge = emptyList()
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
                selectedAge = it.selectedAge
            )
        )
    }

    fun presentSelectedNewSize(id: Int) = viewModel.liveData.value?.let {
            viewModel.liveData.postValue(
                SearchPageViewModelData(
                    state = it.state,
                    listOfSize = it.listOfSize.map { if (it.id == id) it.copy(selected = !it.selected) else it },
                    selectedBreeds = it.selectedBreeds,
                    selectedAge = it.selectedAge
                )
            )
    }

    fun presentSelectedNewAge(id: Int) = viewModel.liveData.value?.let {
        viewModel.liveData.postValue(
            SearchPageViewModelData(
                state = it.state,
                listOfSize = it.listOfSize,
                selectedBreeds = it.selectedBreeds,
                selectedAge = it.selectedAge.map { if (it.id == id) it.copy(selected = !it.selected) else it },
            )
        )
    }
}

@Singleton
class SearchPageViewModel @Inject constructor() : ViewModel(), LifecycleObserver {
    val liveData: MutableLiveData<SearchPageViewModelData> = MutableLiveData()
}