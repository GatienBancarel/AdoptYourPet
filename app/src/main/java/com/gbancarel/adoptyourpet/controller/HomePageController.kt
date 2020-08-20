package com.gbancarel.adoptyourpet.controller

import com.gbancarel.adoptyourpet.interactor.HomePageInteractor
import javax.inject.Inject

interface HomePageControllerInterface {
    fun onCreate()
}

class HomePageController @Inject constructor(
    val interactor: HomePageInteractor
) {

    fun onCreate() {
        interactor.getListAnimal()
    }
}

class HomePageControllerDecorator @Inject constructor(val controller: HomePageController) : HomePageControllerInterface {
    override fun onCreate() {
        Thread {
            controller.onCreate()
        }.start()
    }

}