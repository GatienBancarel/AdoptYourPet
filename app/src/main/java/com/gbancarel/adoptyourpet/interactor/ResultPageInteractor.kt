package com.gbancarel.adoptyourpet.interactor

import com.gbancarel.adoptyourpet.presenter.ResultPagePresenter
import com.gbancarel.adoptyourpet.repository.ListPetRepository
import com.gbancarel.adoptyourpet.repository.error.CannotDecodeJsonException
import com.gbancarel.adoptyourpet.repository.error.ErrorStatusException
import com.gbancarel.adoptyourpet.repository.error.NoInternetConnectionAvailable
import javax.inject.Inject

class ResultPageInteractor @Inject constructor(
    val repository: ListPetRepository,
    val presenter: ResultPagePresenter
) {

    fun getListAnimal(
        animalSelected: String,
        breedsSelected: String,
        sizeSelected: String,
        ageSelected: String,
        colorsSelected: String,
        genderSelected: String
    ) {
        try {
            presenter.presentLoader()
            val listAnimal = repository.searchPet(
                animalSelected,
                breedsSelected,
                sizeSelected,
                ageSelected,
                colorsSelected,
                genderSelected
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
}