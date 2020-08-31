package com.gbancarel.adoptyourpet.controller

import com.gbancarel.adoptyourpet.interactor.HomePageInteractor
import javax.inject.Inject

interface HomePageControllerInterface {
    fun onCreate()
    fun backClicked()
    fun clickedRow(requestedAnimal : String)
}

class HomePageController @Inject constructor(
    val interactor: HomePageInteractor
) {

    fun onCreate() {
        interactor.getListAnimal()
    }

    fun backClicked() {
        interactor.loadPreviousState()
    }

    fun clickedRow(requestedAnimal : String) {
        interactor.loadAnimal(requestedAnimal)
    }
}

class HomePageControllerDecorator @Inject constructor(val controller: HomePageController) : HomePageControllerInterface {
    override fun onCreate() {
        Thread {
            controller.onCreate()
        }.start()
    }

    override fun backClicked() {
        Thread {
            controller.backClicked()
        }.start()
    }

    override fun clickedRow(requestedAnimal : String) {
        Thread {
            controller.clickedRow(requestedAnimal)
        }.start()
    }
}