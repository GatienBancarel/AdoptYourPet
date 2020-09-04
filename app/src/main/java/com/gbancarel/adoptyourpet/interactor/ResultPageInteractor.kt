package com.gbancarel.adoptyourpet.interactor

import com.gbancarel.adoptyourpet.interactor.data.SearchSession
import com.gbancarel.adoptyourpet.presenter.ResultPagePresenter
import com.gbancarel.adoptyourpet.repository.ListPetResultRepository
import com.gbancarel.adoptyourpet.repository.error.CannotDecodeJsonException
import com.gbancarel.adoptyourpet.repository.error.ErrorStatusException
import com.gbancarel.adoptyourpet.repository.error.NoInternetConnectionAvailable
import com.gbancarel.adoptyourpet.state.AnimalSelected
import javax.inject.Inject

class ResultPageInteractor @Inject constructor(
    val repository: ListPetResultRepository,
    val presenter: ResultPagePresenter,
    val session: SearchSession
) {

    fun getListAnimal() {
        try {
            presenter.presentLoader()
            val listAnimal = repository.getListAnimal(
                session.getAnimalType(),
                session.getSelectedBreeds(),
                session.getSize(),
                session.getAges(),
                session.getSelectedColors(),
                session.getSelectedGender()
            )
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
        session.dispose()
    }

    fun loadAnimal(requestedAnimal: String) {
        val localList = repository.getLocalListAnimal()
        presenter.present(
            localList,
            localList.firstOrNull { it.name == requestedAnimal }
        )
    }
}