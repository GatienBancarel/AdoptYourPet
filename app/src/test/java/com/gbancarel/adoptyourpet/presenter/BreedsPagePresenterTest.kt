package com.gbancarel.adoptyourpet.presenter

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.gbancarel.adoptyourpet.interactor.data.listBreeds.BreedLocalInteractor
import com.gbancarel.adoptyourpet.presenter.data.listBreeds.BreedsViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.MockitoAnnotations

class BreedsPagePresenterTest {
    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private var viewModel: BreedsPageViewModel = BreedsPageViewModel()
    private lateinit var presenter: BreedsPagePresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = BreedsPagePresenter(viewModel)
    }

    @Test
    fun presentTest() {
        //WHEN
        presenter.present(
            listBreeds = listOf(
                BreedLocalInteractor("labrador"),
                BreedLocalInteractor("caniche")
            )
        )
        //THEN
        assert(
            viewModel.liveData.value == listOf(
                BreedsViewModel("labrador"),
                BreedsViewModel("caniche")
            )
        )
    }
}