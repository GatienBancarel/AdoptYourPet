package com.gbancarel.adoptyourpet.repository

import com.gbancarel.adoptyourpet.interactor.data.Size
import com.gbancarel.adoptyourpet.state.AnimalSelected
import javax.inject.Inject

class ListSizeRepository @Inject constructor() {
    fun getListSize(animalSelected: AnimalSelected) : List<Size>
         = if (animalSelected == AnimalSelected.dog) {
            listOf(
                Size(0, "Small (0-25 lbs)"),
                 Size(1,"Medium (26-60 lbs)"),
                 Size(2,"Large (61-100 lbs)"),
                 Size(3, "Extra Large (101 lbs or more)")
            )
        } else {
            listOf(
                 Size(4,"Small (0-6 lbs)"),
                 Size(5,"Medium (7-11 lbs)"),
                 Size(6,"Large (12-16 lbs)"),
                 Size(7,"Extra Large (17 lbs or more)")
            )
        }
}