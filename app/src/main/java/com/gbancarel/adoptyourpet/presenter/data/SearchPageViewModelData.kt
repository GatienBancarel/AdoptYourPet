package com.gbancarel.adoptyourpet.presenter.data

import com.gbancarel.adoptyourpet.presenter.data.listBreeds.StateBreedsViewModel

data class SearchPageViewModelData(
    val state : StateBreedsViewModel,
    val selectedBreeds : List<String>
)