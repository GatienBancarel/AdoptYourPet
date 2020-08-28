package com.gbancarel.adoptyourpet.presen

import android.util.Log
import com.gbancarel.adoptyourpet.presenter.SearchPagePresenter
import com.gbancarel.adoptyourpet.presenter.SearchPageViewModel
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.gbancarel.adoptyourpet.presenter.data.SearchPageViewModelData
import com.gbancarel.adoptyourpet.presenter.data.listBreeds.StateBreedsViewModel
import com.gbancarel.adoptyourpet.presenter.data.listSize.SizeViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.MockitoAnnotations

class SearchPagePresenterTest {
    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private var viewModel: SearchPageViewModel = SearchPageViewModel()
    private lateinit var presenter: SearchPagePresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = SearchPagePresenter(viewModel)
    }

    @Test
    fun presentWhenNoBreedSelected() {
        //GIVEN
        //WHEN
        presenter.present()
        //THEN
        assert(
            viewModel.liveData.value == SearchPageViewModelData(
                state = StateBreedsViewModel.finished,
                selectedBreeds = emptyList()
            )
        )
    }

    @Test
    fun presentLoader() {
        //GIVEN
        //WHEN
        presenter.presentLoader()
        //THEN
        assert(
            viewModel.liveData.value == SearchPageViewModelData(
                state = StateBreedsViewModel.loading,
                selectedBreeds = emptyList()
            )
        )
    }

    @Test
    fun presentError() {
        //GIVEN
        //WHEN
        presenter.presentError()
        //THEN
        assert(
            viewModel.liveData.value == SearchPageViewModelData(
                state = StateBreedsViewModel.error,
                selectedBreeds = emptyList()
            )
        )
    }

    @Test
    fun presentWhenBreedSelected() {
        //GIVEN
        //WHEN
        presenter.present(listOf("labrador"))
        //THEN
        assert(
            viewModel.liveData.value == SearchPageViewModelData(
                state = StateBreedsViewModel.finished,
                selectedBreeds = listOf("labrador")
            )
        )
    }

    @Test
    fun presentSize() {
        //GIVEN
        viewModel.liveData.value?.selectedSize = listOf(
            SizeViewModel("Small",0,false),
            SizeViewModel("Medium",1,false),
            SizeViewModel("Large",2,false),
            SizeViewModel("Extra Large",3 ,false)
        )
        //WHEN
        presenter.presentSize("Small",true, 0)
        //THEN

        assert(
            viewModel.liveData.value?.selectedSize == listOf(
                SizeViewModel("Small",0,true),
                SizeViewModel("Medium",1,false),
                SizeViewModel("Large",2,false),
                SizeViewModel("Extra Large",3 ,false)
            )
        )
    }
}