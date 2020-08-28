package com.gbancarel.adoptyourpet.presenter.data

import com.gbancarel.adoptyourpet.presenter.data.listBreeds.StateBreedsViewModel
import com.gbancarel.adoptyourpet.presenter.data.listSize.SizeViewModel

data class SearchPageViewModelData(
    val state : StateBreedsViewModel,
    val selectedBreeds : List<String>,
    var selectedSize: List<SizeViewModel> =
        listOf(
            SizeViewModel("Small",0,false),
            SizeViewModel("Medium",1,false),
            SizeViewModel("Large",2,false),
            SizeViewModel("Extra Large",3 ,false)
        )
)