package com.gbancarel.adoptyourpet.controller

import com.gbancarel.adoptyourpet.interactor.MyInteractor
import javax.inject.Inject

interface MyControllerInterface {
    fun onCreate()
}

class MyController @Inject constructor(
    val interactor: MyInteractor
) {

    fun onCreate() {
        interactor.getCall()
    }
}

class MyControllerDecorator @Inject constructor(val controller: MyController) : MyControllerInterface {
    override fun onCreate() {
        Thread {
            controller.onCreate()
        }.start()
    }

}