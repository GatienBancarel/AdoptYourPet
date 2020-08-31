package com.gbancarel.adoptyourpet.interactor

import com.gbancarel.adoptyourpet.interactor.data.listBreeds.Breeds
import com.gbancarel.adoptyourpet.presenter.SelectedStringPagePresenter
import com.gbancarel.adoptyourpet.repository.ListBreedsRepository
import javax.inject.Inject

class SelectedStringPageInteractor @Inject constructor(
    val repository: ListBreedsRepository,
    val presenter: SelectedStringPagePresenter
) {

    fun updateListBreeds(selectedString: List<String>) {
        val listBreeds: List<Breeds> = repository.getListBreedsLocal()
        presenter.present(listBreeds, selectedString)
    }

    fun updateBreed(name: String, selected: Boolean) {
        presenter.present(name, selected)
    }
}