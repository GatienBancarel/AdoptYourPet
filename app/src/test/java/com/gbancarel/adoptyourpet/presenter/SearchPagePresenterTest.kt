package com.gbancarel.adoptyourpet.presenter

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
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
    fun presentTest() {
        //GIVEN
        //WHEN
        presenter.present()
        //THEN
        assert(viewModel.liveData.value == StateBreedsViewModel.finished)
    }

    @Test
    fun presentLoaderTest() {
        //GIVEN
        //WHEN
        presenter.presentLoader()
        //THEN
        assert(viewModel.liveData.value == StateBreedsViewModel.loading)
    }

    @Test
    fun presentErrorTest() {
        //GIVEN
        //WHEN
        presenter.presentError()
        //THEN
        assert(viewModel.liveData.value == StateBreedsViewModel.error)
    }
}