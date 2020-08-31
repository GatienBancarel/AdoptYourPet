package com.gbancarel.adoptyourpet.repository

import com.gbancarel.adoptyourpet.interactor.data.Age
import javax.inject.Inject

class ListAgeRepository @Inject constructor() {
    fun getListAge() = listOf(
        Age(0,"Kitten"),
        Age(1,"Young"),
        Age(2,"Adult"),
        Age(3,"Senior")
    )
}