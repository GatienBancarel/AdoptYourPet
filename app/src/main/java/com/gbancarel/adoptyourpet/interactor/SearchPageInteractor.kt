package com.gbancarel.adoptyourpet.interactor

import com.gbancarel.adoptyourpet.presenter.SearchPagePresenter
import com.gbancarel.adoptyourpet.repository.*
import com.gbancarel.adoptyourpet.repository.error.CannotDecodeJsonException
import com.gbancarel.adoptyourpet.repository.error.ErrorStatusException
import com.gbancarel.adoptyourpet.repository.error.NoInternetConnectionAvailable
import com.gbancarel.adoptyourpet.state.AnimalSelected
import javax.inject.Inject

class SearchPageInteractor @Inject constructor(
    val breedsRepository: ListBreedsRepository,
    val sizeRepository: ListSizeRepository,
    val ageRepository: ListAgeRepository,
    val colorsRepository: ListColorsRepository,
    val presenter: SearchPagePresenter
) {

    fun load(animalSelected: AnimalSelected) {
        try {
            presenter.presentBreedsAndColorsLoader()
            val sizes = sizeRepository.getListSize(animalSelected)
            val ages = ageRepository.getListAge()
            presenter.present(sizes, ages)
            breedsRepository.loadBreeds(animalSelected)
            colorsRepository.loadColors(animalSelected)
            presenter.presentBreedsAndColorsLoaderFinished()
        } catch (e1: CannotDecodeJsonException) {
            presenter.presentError()
        } catch (e1: ErrorStatusException) {
            presenter.presentError()
        } catch (e1: NoInternetConnectionAvailable) {
            presenter.presentError()
        }
    }

    fun selectBreeds(breeds: List<String>) {
        presenter.presentSelectBreeds(breeds)
    }

    fun selectedSize(id : Int) {
        presenter.presentSelectedNewSize(id)
    }

    fun selectedAge(id : Int) {
        presenter.presentSelectedNewAge(id)
    }

    fun selectedColors(colors: List<String>) {
        presenter.presentSelectColors(colors)
    }

    fun selectedGender() {
        presenter.presentNewGender()
    }
}