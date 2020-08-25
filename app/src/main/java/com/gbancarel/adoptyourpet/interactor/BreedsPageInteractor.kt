package com.gbancarel.adoptyourpet.interactor

import android.util.Log
import com.gbancarel.adoptyourpet.presenter.BreedsPagePresenter
import com.gbancarel.adoptyourpet.repository.BreedsPageRepository
import com.gbancarel.adoptyourpet.repository.error.CannotDecodeJsonException
import com.gbancarel.adoptyourpet.repository.error.ErrorStatusException
import com.gbancarel.adoptyourpet.repository.error.NoInternetConnectionAvailable
import javax.inject.Inject

class BreedsPageInteractor @Inject constructor(
    val repository: BreedsPageRepository,
    val presenter: BreedsPagePresenter
) {

    fun getListBreeds() {
        try {
            Log.i("mylog", "je suis dans l'interactor")
            //presenter.presentLoader()
            val listBreeds = repository.getListBreeds()
            presenter.present(listBreeds)
        } catch (e1: CannotDecodeJsonException) {
            Log.i("mylog","error")
            //presenter.presentError()
        } catch (e1: ErrorStatusException) {
            Log.i("mylog","error")
            //presenter.presentError()
        } catch (e1: NoInternetConnectionAvailable) {
            Log.i("mylog","error")
            //presenter.presentError()
        }
    }
}