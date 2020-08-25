package com.gbancarel.adoptyourpet.interactor

import com.gbancarel.adoptyourpet.presenter.SearchPagePresenter
import com.gbancarel.adoptyourpet.repository.ListBreedsRepository
import com.gbancarel.adoptyourpet.repository.error.CannotDecodeJsonException
import com.gbancarel.adoptyourpet.repository.error.ErrorStatusException
import com.gbancarel.adoptyourpet.repository.error.NoInternetConnectionAvailable
import javax.inject.Inject

class SearchPageInteractor @Inject constructor(
    val repository: ListBreedsRepository,
    val presenter: SearchPagePresenter
) {

    fun getListBreeds(animalSelected: String) {
        try {
            presenter.presentLoader()
            repository.getListBreeds(animalSelected)
            presenter.present()
        } catch (e1: CannotDecodeJsonException) {
            presenter.presentError()
        } catch (e1: ErrorStatusException) {
            presenter.presentError()
        } catch (e1: NoInternetConnectionAvailable) {
            presenter.presentError()
        }
    }
}