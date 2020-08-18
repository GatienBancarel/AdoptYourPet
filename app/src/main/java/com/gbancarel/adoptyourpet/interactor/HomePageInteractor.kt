package com.gbancarel.adoptyourpet.interactor

import com.gbancarel.adoptyourpet.presenter.HomePagePresenter
import com.gbancarel.adoptyourpet.repository.error.CannotDecodeJsonException
import com.gbancarel.adoptyourpet.repository.error.ErrorStatusException
import com.gbancarel.adoptyourpet.repository.HomePageRepository
import javax.inject.Inject

class HomePageInteractor @Inject constructor(
    val repository: HomePageRepository,
    val presenter: HomePagePresenter
) {

    fun getCall() {
        try {

            val call = repository.getCall()
            presenter.present(call)
        } catch (e1: CannotDecodeJsonException) {
            presenter.presentErrorOkHttp()
        } catch (e1: ErrorStatusException) {
            presenter.presentErrorMoshi()
        }
    }
}