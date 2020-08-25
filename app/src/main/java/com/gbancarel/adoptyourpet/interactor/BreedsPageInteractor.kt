package com.gbancarel.adoptyourpet.interactor

import com.gbancarel.adoptyourpet.interactor.data.listBreeds.BreedLocalInteractor
import com.gbancarel.adoptyourpet.presenter.BreedsPagePresenter
import com.gbancarel.adoptyourpet.repository.ListBreedsRepository
import javax.inject.Inject

class BreedsPageInteractor @Inject constructor(
    val repository: ListBreedsRepository,
    val presenter: BreedsPagePresenter
) {

    fun getListBreeds() {
        val listBreeds: List<BreedLocalInteractor> = repository.getListBreedsLocal()
        presenter.present(listBreeds)
    }
}