package com.gbancarel.adoptyourpet.interactor

import android.util.Log
import com.gbancarel.adoptyourpet.presenter.SearchPagePresenter
import com.gbancarel.adoptyourpet.repository.ListAgeRepository
import com.gbancarel.adoptyourpet.repository.ListBreedsRepository
import com.gbancarel.adoptyourpet.repository.ListSizeRepository
import com.gbancarel.adoptyourpet.repository.ListColorsRepository
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
            presenter.presentBreedsLoader()
            val sizes = sizeRepository.getListSize(animalSelected)
            val ages = ageRepository.getListAge()
            presenter.present(sizes, ages)

            breedsRepository.loadBreeds(animalSelected)
            presenter.presentBreedsLoaderFinished()
        } catch (e1: CannotDecodeJsonException) {
            presenter.presentBreedsError()
        } catch (e1: ErrorStatusException) {
            presenter.presentBreedsError()
        } catch (e1: NoInternetConnectionAvailable) {
            presenter.presentBreedsError()
        }
    }

    fun getListColors(animalSelected: String) {
        try {
            Log.i("mylog","present loader colors")
            presenter.presentColorsLoader()
            val call = repositoryColors.loadColors(animalSelected)
            Log.i("mylog","present colors")
            presenter.presentColors(call)
        } catch (e1: CannotDecodeJsonException) {
            presenter.presentColorsError()
        } catch (e1: ErrorStatusException) {
            presenter.presentColorsError()
        } catch (e1: NoInternetConnectionAvailable) {
            presenter.presentColorsError()
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

    fun selectedAge(age: String, selected: Boolean, order: Int) {
        presenter.presentAge(age,selected,order)
    }

    fun selectedColors(colors: String, selected: Boolean) {
        presenter.presentSelectedColors(colors,selected)
    }
}