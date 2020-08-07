package com.gbancarel.adoptyourpet.interactor

import android.util.Log
import com.gbancarel.adoptyourpet.presenter.MyPresenter
import com.gbancarel.adoptyourpet.repository.error.CannotDecodeJsonException
import com.gbancarel.adoptyourpet.repository.error.ErrorStatusException
import com.gbancarel.adoptyourpet.repository.MyRepository
import javax.inject.Inject

class MyInteractor @Inject constructor(
    val repository: MyRepository,
    val presenter: MyPresenter
) {

    fun getCall() {
        try {
            val call = repository.getCall()
            //products.filter { it.price < 500 }
            presenter.present(call)
        } catch (e1: CannotDecodeJsonException) {
            presenter.presentErrorOkHttp()
        } catch (e1: ErrorStatusException) {
            presenter.presentErrorMoshi()
        }
    }
}