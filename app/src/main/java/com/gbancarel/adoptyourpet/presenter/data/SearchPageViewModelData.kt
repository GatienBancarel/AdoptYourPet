package com.gbancarel.adoptyourpet.presenter.data

import com.gbancarel.adoptyourpet.presenter.data.listBreeds.StateBreedsViewModel
import com.gbancarel.adoptyourpet.presenter.data.listSize.SizeViewModel

data class SearchPageViewModelData(
    val state : StateBreedsViewModel,
    val listOfSize: List<SizeViewModel>,
    val selectedBreeds : List<String>,
)