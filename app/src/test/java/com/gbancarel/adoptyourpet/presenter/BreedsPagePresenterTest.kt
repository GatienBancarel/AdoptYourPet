package com.gbancarel.adoptyourpet.presenter

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.gbancarel.adoptyourpet.interactor.data.listBreeds.Breeds
import com.gbancarel.adoptyourpet.presenter.data.listBreeds.BreedsViewModelData
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
    fun presentIntialList() {
        //GIVEN
        //WHEN
        presenter.present(
            listBreeds = listOf(
                Breeds("labrador"),
                Breeds("caniche")
            ),
            selectedBreeds = listOf(
                "labrador"
            )
        )
        //THEN
        assert(
            viewModel.liveData.value == listOf(
                BreedsViewModelData("caniche"),
                BreedsViewModelData("labrador",true)
            )
        )
    }

    @Test
    fun presentListWhenItemIsUpdated() {
        //GIVEN
        viewModel.liveData.value = listOf(
            BreedsViewModelData("labrador", false),
            BreedsViewModelData("caniche", false),
            BreedsViewModelData("staff", false)
        )
        //WHEN
        presenter.present("labrador", true)
        //THEN
        assert(
            viewModel.liveData.value == listOf(
                BreedsViewModelData("caniche", false),
                BreedsViewModelData("labrador", true),
                BreedsViewModelData("staff", false)
            )
        )
    }
}