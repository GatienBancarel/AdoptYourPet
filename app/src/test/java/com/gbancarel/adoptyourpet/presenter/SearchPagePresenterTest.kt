package com.gbancarel.adoptyourpet.presen

import com.gbancarel.adoptyourpet.presenter.SearchPagePresenter
import com.gbancarel.adoptyourpet.presenter.SearchPageViewModel
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.gbancarel.adoptyourpet.interactor.data.Age
import com.gbancarel.adoptyourpet.interactor.data.Size
import com.gbancarel.adoptyourpet.presenter.data.SearchPageViewModelData
import com.gbancarel.adoptyourpet.presenter.data.listAge.AgeViewModel
import com.gbancarel.adoptyourpet.presenter.data.listBreeds.StateSearchPageViewModel
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
    fun presentBreedsAndColorsLoader() {
        //GIVEN
        //WHEN
        presenter.presentBreedsAndColorsLoader()
        //THEN
        assertEquals(
            viewModel.liveData.value, SearchPageViewModelData(
                state = StateSearchPageViewModel.loading,
                listOfSize = emptyList(),
                selectedBreeds = emptyList(),
                selectedAge = emptyList(),
                selectedColors = emptyList()
            )
        )
    }

    @Test
    fun presentBreedsAndColorsLoaderFinished() {
        //GIVEN
        val sizes = listOf(
            SizeViewModel(0, "Small")
        )
        viewModel.liveData.value = SearchPageViewModelData(
            state = StateSearchPageViewModel.error,
            listOfSize = sizes,
            selectedBreeds = emptyList(),
            selectedAge = emptyList(),
            selectedColors = emptyList()
        )

        //WHEN
        presenter.presentBreedsAndColorsLoaderFinished()
        //THEN
        assertEquals(
            viewModel.liveData.value, SearchPageViewModelData(
                state = StateSearchPageViewModel.finished,
                listOfSize = sizes,
                selectedBreeds = emptyList(),
                selectedAge = emptyList(),
                selectedColors = emptyList()
            )
        )
    }

    @Test
    fun presentBreedsAndColorsLoaderFinishedWhenLiveDataNull() {
        //GIVEN

        //WHEN
        presenter.presentBreedsAndColorsLoaderFinished()
        //THEN
        assertEquals(
            viewModel.liveData.value,
            SearchPageViewModelData(
                state = StateSearchPageViewModel.finished,
                listOfSize = emptyList(),
                selectedBreeds = emptyList(),
                selectedAge = emptyList(),
                selectedColors = emptyList()
            )
        )
    }

    @Test
    fun presentSizes() {
        //GIVEN

        //WHEN
        presenter.present(listOf(Size(0, "Small")), listOf(Age(0, "Young")))

        //THEN
        assertEquals(
            viewModel.liveData.value, SearchPageViewModelData(
                state = StateSearchPageViewModel.loading,
                listOfSize = listOf(SizeViewModel(0,"Small")),
                selectedBreeds = emptyList(),
                selectedAge = listOf(AgeViewModel(0, "Young", false)),
                selectedColors = emptyList()
            )
        )
    }

    @Test
    fun presentError() {
        //GIVEN
        //WHEN
        presenter.presentError()
        //THEN
        assertEquals(
            viewModel.liveData.value, SearchPageViewModelData(
                state = StateSearchPageViewModel.error,
                listOfSize = emptyList(),
                selectedBreeds = emptyList(),
                selectedAge = emptyList(),
                selectedColors = emptyList()
            )
        )
    }

    @Test
    fun presentSelectBreeds() {
        //GIVEN
        viewModel.liveData.value = SearchPageViewModelData(
            StateSearchPageViewModel.finished,
            listOf(SizeViewModel(1,"small")),
            listOf("labrador"),
            listOf(AgeViewModel(1,"young", false)),
            listOf("brown")
        )

        //WHEN
        presenter.presentSelectBreeds(listOf("caniche"))

        //THEN
        assertEquals(
            viewModel.liveData.value, SearchPageViewModelData(
                state = StateSearchPageViewModel.finished,
                listOfSize = listOf(SizeViewModel(1, "small")),
                selectedBreeds = listOf("caniche"),
                selectedAge = listOf(AgeViewModel(1,"young", false)),
                listOf("brown")
            )
        )
    }

    @Test
    fun presentSelectedNewSize() {
        //GIVEN
        viewModel.liveData.value = SearchPageViewModelData(
            StateSearchPageViewModel.error,
            listOf(
                SizeViewModel(0,"Small",false),
                SizeViewModel(1,"Medium",false),
                SizeViewModel(2,"Large",false),
                SizeViewModel(3,"Extra Large",false)
            ),
            listOf("labrador"),
            listOf(AgeViewModel(1,"young", false)),
            listOf("brown")
        )

        //WHEN
        presenter.presentSelectedNewSize(0)

        //THEN
        assertEquals(
            viewModel.liveData.value,
            SearchPageViewModelData(
                StateSearchPageViewModel.error,
                listOf(
                    SizeViewModel(0,"Small",true),
                    SizeViewModel(1,"Medium",false),
                    SizeViewModel(2,"Large",false),
                    SizeViewModel(3,"Extra Large",false)
                ),
                listOf("labrador"),
                listOf(AgeViewModel(1,"young", false)),
                listOf("brown")
            )
        )
    }

    @Test
    fun presentSelectedNewAge() {
        //GIVEN
        viewModel.liveData.value = SearchPageViewModelData(
            state = StateSearchPageViewModel.error,
            listOfSize = listOf(SizeViewModel(0, "Small")),
            selectedBreeds = listOf("labrador"),
            selectedAge = listOf(
                AgeViewModel(0, "Kitten", selected = false),
            ),
            selectedColors = listOf("brown")
        )

        //WHEN
        presenter.presentSelectedNewAge(0)

        //THEN
        assertEquals(
            viewModel.liveData.value,
            SearchPageViewModelData(
                state = StateSearchPageViewModel.error,
                listOfSize = listOf(SizeViewModel(0, "Small")),
                selectedBreeds = listOf("labrador"),
                selectedAge = listOf(
                    AgeViewModel(0, "Kitten", selected = true),
                ),
                selectedColors = listOf("brown")
            )
        )
    }

    @Test
    fun presentSelectColors() {
        //GIVEN
        viewModel.liveData.value = SearchPageViewModelData(
            StateSearchPageViewModel.finished,
            listOf(SizeViewModel(1,"small")),
            listOf("labrador"),
            listOf(AgeViewModel(1,"young", false)),
            listOf("brown")
        )

        //WHEN
        presenter.presentSelectColors(listOf("yellow"))

        //THEN
        assertEquals(
            viewModel.liveData.value, SearchPageViewModelData(
                state = StateSearchPageViewModel.finished,
                listOfSize = listOf(SizeViewModel(1, "small")),
                selectedBreeds = listOf("labrador"),
                selectedAge = listOf(AgeViewModel(1,"young", false)),
                listOf("yellow")
            )
        )
    }
}