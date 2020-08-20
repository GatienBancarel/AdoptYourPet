package com.gbancarel.adoptyourpet.interactor

import com.gbancarel.adoptyourpet.presenter.HomePagePresenter
import com.gbancarel.adoptyourpet.repository.error.CannotDecodeJsonException
import com.gbancarel.adoptyourpet.repository.error.ErrorStatusException
import com.gbancarel.adoptyourpet.repository.HomePageRepository
import com.gbancarel.adoptyourpet.repository.error.NoInternetConnectionAvailable
import javax.inject.Inject

class HomePageInteractor @Inject constructor(
    val repository: HomePageRepository,
    val presenter: HomePagePresenter
) {

    fun getListAnimal() {
        try {
            presenter.presentLoader()
            val listAnimal = repository.getListAnimal()
            presenter.present(listAnimal)
        } catch (e1: CannotDecodeJsonException) {
            presenter.presentError()
        } catch (e1: ErrorStatusException) {
            presenter.presentError()
        } catch (e1: NoInternetConnectionAvailable) {
            presenter.presentError()
        }
    }
}