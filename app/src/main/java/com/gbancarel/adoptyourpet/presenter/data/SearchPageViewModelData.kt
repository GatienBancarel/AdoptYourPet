package com.gbancarel.adoptyourpet.presenter.data

import com.gbancarel.adoptyourpet.presenter.data.listAge.AgeViewModel
import com.gbancarel.adoptyourpet.presenter.data.listBreeds.StateSearchPageViewModel
import com.gbancarel.adoptyourpet.presenter.data.listGender.GenderViewModel
import com.gbancarel.adoptyourpet.presenter.data.listSize.SizeViewModel

data class SearchPageViewModelData(
    val state : StateSearchPageViewModel,
    val listOfSize: List<SizeViewModel>,
    val selectedBreeds : List<String>,
    val selectedAge: List<AgeViewModel>,
    val selectedColors: List<String>,
    val selectedGender: GenderViewModel
)