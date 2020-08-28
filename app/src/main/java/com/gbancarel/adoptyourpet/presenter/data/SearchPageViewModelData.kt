package com.gbancarel.adoptyourpet.presenter.data

import com.gbancarel.adoptyourpet.presenter.data.listAge.AgeViewModel
import com.gbancarel.adoptyourpet.presenter.data.listBreeds.StateBreedsViewModel
import com.gbancarel.adoptyourpet.presenter.data.listSize.SizeViewModel

data class SearchPageViewModelData(
    val state : StateBreedsViewModel,
    val listOfSize: List<SizeViewModel>,
    val selectedBreeds : List<String>,
    var selectedAge: List<AgeViewModel> =
        listOf(
            AgeViewModel("Kitten", selected = false, 0),
            AgeViewModel("Young", selected = false, 1),
            AgeViewModel("Adult", selected = false, 2),
            AgeViewModel("Senior", selected = false, 3)
        )
)