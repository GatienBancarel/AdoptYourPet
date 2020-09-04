package com.gbancarel.adoptyourpet.controller

import com.gbancarel.adoptyourpet.interactor.ResultPageInteractor
import javax.inject.Inject

interface ResultPageControllerInterface {
    fun onCreate()
    fun backClicked()
    fun clickedRow(requestedAnimal : String)

}

class ResultPageController @Inject constructor(
    val interactor: ResultPageInteractor
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

class ResultPageControllerDecorator @Inject constructor(val controller: ResultPageController) : ResultPageControllerInterface {
    override fun onCreate(
    ) {
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