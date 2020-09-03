package com.gbancarel.adoptyourpet.controller

import com.gbancarel.adoptyourpet.interactor.ResultPageInteractor
import javax.inject.Inject

interface ResultPageControllerInterface {
    fun onCreate(
        animalSelected: String,
        breedsSelected: String,
        SizeSelected: String,
        AgeSelected: String,
        ColorsSelected: String,
        GenderSelected: String
    )

}

class ResultPageController @Inject constructor(
    val interactor: ResultPageInteractor
) {

    fun onCreate(
        animalSelected: String,
        breedsSelected: String,
        sizeSelected: String,
        ageSelected: String,
        colorsSelected: String,
        genderSelected: String
    ) {
        interactor.getListAnimal(
            animalSelected,
            breedsSelected,
            sizeSelected,
            ageSelected,
            colorsSelected,
            genderSelected
        )
    }


}

class ResultPageControllerDecorator @Inject constructor(val controller: ResultPageController) : ResultPageControllerInterface {
    override fun onCreate(
        animalSelected: String,
        breedsSelected: String,
        sizeSelected: String,
        ageSelected: String,
        colorsSelected: String,
        genderSelected: String
    ) {
        Thread {
            controller.onCreate(
                animalSelected,
                breedsSelected,
                sizeSelected,
                ageSelected,
                colorsSelected,
                genderSelected
            )
        }.start()
    }

}