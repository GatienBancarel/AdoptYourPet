package com.gbancarel.adoptyourpet.presen

import android.util.Log
import com.gbancarel.adoptyourpet.presenter.SearchPagePresenter
import com.gbancarel.adoptyourpet.presenter.SearchPageViewModel
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.gbancarel.adoptyourpet.interactor.data.Size
import com.gbancarel.adoptyourpet.presenter.data.SearchPageViewModelData
import com.gbancarel.adoptyourpet.presenter.data.listBreeds.StateBreedsViewModel
import com.gbancarel.adoptyourpet.presenter.data.listSize.SizeViewModel
import junit.framework.Assert.assertEquals
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
    fun presentBreedsLoader() {
        //GIVEN
        //WHEN
        presenter.presentBreedsLoader()
        //THEN
        assert(
            viewModel.liveData.value == SearchPageViewModelData(
                state = StateBreedsViewModel.loading,
                listOfSize = emptyList(),
                selectedBreeds = emptyList()
            )
        )
    }

    @Test
    fun presentBreedsLoaderFinished() {
        //GIVEN
        val sizes = listOf(
            SizeViewModel(0, "Small")
        )
        viewModel.liveData.value = SearchPageViewModelData(
            state = StateBreedsViewModel.error,
            listOfSize = sizes,
            selectedBreeds = emptyList()
        )

        //WHEN
        presenter.presentBreedsLoaderFinished()
        //THEN
        assert(
            viewModel.liveData.value == SearchPageViewModelData(
                state = StateBreedsViewModel.finished,
                listOfSize = sizes,
                selectedBreeds = emptyList()
            )
        )
    }

    @Test
    fun presentBreedsLoaderFinishedWhenLiveDataNull() {
        //GIVEN

        //WHEN
        presenter.presentBreedsLoaderFinished()
        //THEN
        assert(
            viewModel.liveData.value == SearchPageViewModelData(
                state = StateBreedsViewModel.finished,
                listOfSize = emptyList(),
                selectedBreeds = emptyList()
            )
        )
    }

    @Test
    fun presentSizes() {
        //GIVEN

        //WHEN
        presenter.presentSizes(listOf(Size(0, "Small")))

        //THEN
        assert(
            viewModel.liveData.value == SearchPageViewModelData(
                state = StateBreedsViewModel.loading,
                listOfSize = listOf(SizeViewModel(0,"Small")),
                selectedBreeds = emptyList(),
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
                listOfSize = emptyList(),
                selectedBreeds = emptyList()
            )
        )
    }

    @Test
    fun presentSelectBreeds() {
        //GIVEN
        viewModel.liveData.value = SearchPageViewModelData(
            StateBreedsViewModel.finished,
            listOf(SizeViewModel(1,"small")),
            listOf("labrador"),
        )

        //WHEN
        presenter.presentSelectBreeds(listOf("caniche"))

        //THEN
        assert(
            viewModel.liveData.value == SearchPageViewModelData(
                state = StateBreedsViewModel.finished,
                listOfSize = listOf(SizeViewModel(1, "small")),
                selectedBreeds = listOf("caniche"),
            )
        )
    }

    @Test
    fun presentSelectedNewSize() {
        //GIVEN
        viewModel.liveData.value = SearchPageViewModelData(
            StateBreedsViewModel.error,
            listOf(
                SizeViewModel(0,"Small",false),
                SizeViewModel(1,"Medium",false),
                SizeViewModel(2,"Large",false),
                SizeViewModel(3,"Extra Large",false)
            ),
            listOf("labrador"),
        )

        //WHEN
        presenter.presentSelectedNewSize(0)

        //THEN
        assertEquals(
            viewModel.liveData.value?.listOfSize,
            listOf(
                SizeViewModel(0,"Small",true),
                SizeViewModel(1,"Medium",false),
                SizeViewModel(2,"Large",false),
                SizeViewModel(3,"Extra Large",false)
            )
        )
    }
}