package com.gbancarel.adoptyourpet.presenter

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.gbancarel.adoptyourpet.presenter.data.SearchPageViewModelData
import com.gbancarel.adoptyourpet.presenter.data.listBreeds.StateBreedsViewModel
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
}