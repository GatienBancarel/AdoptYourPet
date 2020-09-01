package com.gbancarel.adoptyourpet.presenter

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gbancarel.adoptyourpet.interactor.data.Age
import com.gbancarel.adoptyourpet.interactor.data.Size
import com.gbancarel.adoptyourpet.presenter.data.SearchPageViewModelData
import com.gbancarel.adoptyourpet.presenter.data.listAge.AgeViewModel
import com.gbancarel.adoptyourpet.presenter.data.listBreeds.StateSearchPageViewModel
import com.gbancarel.adoptyourpet.presenter.data.listSize.SizeViewModel
import javax.inject.Inject
import javax.inject.Singleton

class SearchPagePresenter @Inject constructor(
    val viewModel: SearchPageViewModel
) {

    val defaultGender = GenderViewModel.male

    fun presentBreedsAndColorsLoader() {
        val stateBreedsViewModel = StateSearchPageViewModel.loading
        viewModel.liveData.postValue(
            SearchPageViewModelData(
                state = stateBreedsViewModel,
                listOfSize = emptyList(),
                selectedBreeds = emptyList(),
                selectedAge = emptyList(),
                selectedColors = emptyList(),
                selectedGender = defaultGender
            )
        )
    }

    fun present(sizes: List<Size>, ages: List<Age>) {
        viewModel.liveData.postValue(
            SearchPageViewModelData(
                state = StateSearchPageViewModel.loading,
                listOfSize = sizes.map { SizeViewModel(it.id, it.value) },
                selectedBreeds = emptyList(),
                selectedAge = ages.map { AgeViewModel(it.id, it.value, false) },
                selectedColors = emptyList(),
                selectedGender = defaultGender
            )
        )
    }

    fun presentBreedsAndColorsLoaderFinished() {
        val stateBreedsViewModel = StateSearchPageViewModel.finished
        viewModel.liveData.postValue(
            SearchPageViewModelData(
                state = stateBreedsViewModel,
                listOfSize = viewModel.liveData.value?.listOfSize.orEmpty(),
                selectedBreeds = emptyList(),
                selectedAge = viewModel.liveData.value?.selectedAge.orEmpty(),
                selectedColors = emptyList(),
                selectedGender = viewModel.liveData.value?.selectedGender ?: defaultGender
            )
        )
    }

    fun presentError() {
        val stateBreedsViewModel = StateSearchPageViewModel.error
        viewModel.liveData.postValue(
            SearchPageViewModelData(
                state = stateBreedsViewModel,
                listOfSize = emptyList(),
                selectedBreeds = emptyList(),
                selectedAge = emptyList(),
                selectedColors = emptyList(),
                selectedGender = defaultGender
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
                selectedAge = it.selectedAge,
                selectedColors = it.selectedColors,
                selectedGender = it.selectedGender
            )
        )
    }

    fun presentSelectedNewSize(id: Int) = viewModel.liveData.value?.let {
        viewModel.liveData.postValue(
            SearchPageViewModelData(
                state = it.state,
                listOfSize = it.listOfSize.map { if (it.id == id) it.copy(selected = !it.selected) else it },
                selectedBreeds = it.selectedBreeds,
                selectedAge = it.selectedAge,
                selectedColors = it.selectedColors,
                selectedGender = it.selectedGender
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
                selectedColors = it.selectedColors,
                selectedGender = it.selectedGender
            )
        )
    }

    fun presentSelectColors(colors: List<String>) = viewModel.liveData.value?.let {
        viewModel.liveData.postValue(
            SearchPageViewModelData(
                state = it.state,
                listOfSize = it.listOfSize,
                selectedBreeds = it.selectedBreeds,
                selectedAge = it.selectedAge,
                selectedColors = colors,
                selectedGender = it.selectedGender
            )
        )
    }

    fun presentNewGender() = viewModel.liveData.value?.let {
        viewModel.liveData.postValue(
            SearchPageViewModelData(
                state = it.state,
                listOfSize = it.listOfSize,
                selectedBreeds = it.selectedBreeds,
                selectedAge = it.selectedAge,
                selectedColors = it.selectedColors,
                selectedGender = if (it.selectedGender == GenderViewModel.male) GenderViewModel.female else GenderViewModel.male
            )
        )
    }
}

@Singleton
class SearchPageViewModel @Inject constructor() : ViewModel(), LifecycleObserver {
    val liveData: MutableLiveData<SearchPageViewModelData> = MutableLiveData()
}