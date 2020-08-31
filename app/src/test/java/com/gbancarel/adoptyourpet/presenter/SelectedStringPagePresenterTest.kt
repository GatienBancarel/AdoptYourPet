package com.gbancarel.adoptyourpet.presenter

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.gbancarel.adoptyourpet.interactor.data.listBreeds.Breeds
import com.gbancarel.adoptyourpet.presenter.data.listBreeds.StringSelectedViewModelData
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.MockitoAnnotations

class SelectedStringPagePresenterTest {
    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private var viewModel: BreedsPageViewModel = BreedsPageViewModel()
    private lateinit var presenter: SelectedStringPagePresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = SelectedStringPagePresenter(viewModel)
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
        assertEquals(
            viewModel.liveData.value, listOf(
                StringSelectedViewModelData("caniche"),
                StringSelectedViewModelData("labrador",true)
            )
        )
    }

    @Test
    fun presentListWhenItemIsUpdated() {
        //GIVEN
        viewModel.liveData.value = listOf(
            StringSelectedViewModelData("labrador", false),
            StringSelectedViewModelData("caniche", false),
            StringSelectedViewModelData("staff", false)
        )
        //WHEN
        presenter.present("labrador", true)
        //THEN
        assertEquals(
            viewModel.liveData.value, listOf(
                StringSelectedViewModelData("caniche", false),
                StringSelectedViewModelData("labrador", true),
                StringSelectedViewModelData("staff", false)
            )
        )
    }
}