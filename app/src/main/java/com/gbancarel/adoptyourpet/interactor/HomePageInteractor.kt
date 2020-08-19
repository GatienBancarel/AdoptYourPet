package com.gbancarel.adoptyourpet.interactor

import android.os.Build
import androidx.annotation.RequiresApi
import com.gbancarel.adoptyourpet.presenter.HomePagePresenter
import com.gbancarel.adoptyourpet.repository.error.CannotDecodeJsonException
import com.gbancarel.adoptyourpet.repository.error.ErrorStatusException
import com.gbancarel.adoptyourpet.repository.HomePageRepository
import javax.inject.Inject

class HomePageInteractor @Inject constructor(
    val repository: HomePageRepository,
    val presenter: HomePagePresenter
) {

    @RequiresApi(Build.VERSION_CODES.M)
    fun getListAnimal() {
        try {
            val listAnimal = repository.getListAnimal()
            presenter.present(listAnimal)
        } catch (e1: CannotDecodeJsonException) {
            presenter.presentErrorOkHttp()
        } catch (e1: ErrorStatusException) {
            presenter.presentErrorMoshi()
        }
    }
}