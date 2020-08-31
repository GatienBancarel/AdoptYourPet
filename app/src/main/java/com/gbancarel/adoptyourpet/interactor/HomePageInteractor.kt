package com.gbancarel.adoptyourpet.interactor

import com.gbancarel.adoptyourpet.presenter.HomePagePresenter
import com.gbancarel.adoptyourpet.repository.error.CannotDecodeJsonException
import com.gbancarel.adoptyourpet.repository.error.ErrorStatusException
import com.gbancarel.adoptyourpet.repository.ListPetRepository
import com.gbancarel.adoptyourpet.repository.error.NoInternetConnectionAvailable
import javax.inject.Inject

class HomePageInteractor @Inject constructor(
    val repository: ListPetRepository,
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

    fun loadPreviousState() {
        val listAnimal = repository.getLocalListAnimal()
        presenter.present(listAnimal)
    }

    fun loadAnimal(requestedAnimal: String) {
        val localList = repository.getLocalListAnimal()
        presenter.present(
            localList,
            localList.firstOrNull { it.name == requestedAnimal }
        )
    }
}