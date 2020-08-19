package com.gbancarel.adoptyourpet.controller

import android.os.Build
import androidx.annotation.RequiresApi
import com.gbancarel.adoptyourpet.interactor.HomePageInteractor
import javax.inject.Inject

interface HomePageControllerInterface {
    fun onCreate()
}

class HomePageController @Inject constructor(
    val interactor: HomePageInteractor
) {

    @RequiresApi(Build.VERSION_CODES.M)
    fun onCreate() {
        interactor.getListAnimal()
    }
}

class HomePageControllerDecorator @Inject constructor(val controller: HomePageController) : HomePageControllerInterface {
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate() {
        Thread {
            controller.onCreate()
        }.start()
    }

}