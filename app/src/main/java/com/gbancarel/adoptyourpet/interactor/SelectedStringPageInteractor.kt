package com.gbancarel.adoptyourpet.interactor

import com.gbancarel.adoptyourpet.interactor.data.listBreeds.Breeds
import com.gbancarel.adoptyourpet.presenter.SelectedStringPagePresenter
import com.gbancarel.adoptyourpet.repository.ListBreedsRepository
import com.gbancarel.adoptyourpet.repository.ListColorsRepository
import javax.inject.Inject

class SelectedStringPageInteractor @Inject constructor(
    val breedsRepository: ListBreedsRepository,
    val colorsRepository: ListColorsRepository,
    val presenter: SelectedStringPagePresenter
) {

    fun updateListString(selectedString: List<String>, title: String) {
        if (title == "breeds") {
            val listBreeds: List<Breeds> = breedsRepository.getListBreedsLocal()
            presenter.presentBreeds(listBreeds, selectedString)
        } else {
            val listColors = colorsRepository.getListColorsLocal()
            presenter.presentColors(listColors, selectedString)
        }
    }

    fun updateBreed(name: String, selected: Boolean) {
        presenter.present(name, selected)
    }
}