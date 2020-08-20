package com.gbancarel.adoptyourpet.presenter

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.gbancarel.adoptyourpet.interactor.data.*
import com.gbancarel.adoptyourpet.presenter.data.PetAnimalViewModel
import com.gbancarel.adoptyourpet.presenter.data.PetFinderViewModel
import com.gbancarel.adoptyourpet.presenter.data.PetFinderViewModelState
import com.gbancarel.adoptyourpet.presenter.data.PhotoViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class HomePagePresenterTest {
    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @Mock
    private lateinit var context: Context
    private var viewModel: HomePageViewModel = HomePageViewModel()
    private lateinit var presenter: HomePagePresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = HomePagePresenter(viewModel, context)
    }

    @Test
    fun present() {
        // WHEN
        presenter.present(
            listOf(
                PetAnimal(
                    type = "dog",
                    breed = "labrador",
                    color = "brown",
                    age = "young",
                    gender = "male",
                    size = "meduim",
                    name = "rex",
                    description = "description du chien",
                    environment = Environment(true, true, true),
                    photos = listOf(
                        Photo(
                            "pictureSmall",
                            "pictureMedium",
                            "pictureLarge",
                            "pictureFull"
                        )
                    ),
                    contact = Contact(
                        "test@test.fr",
                        "01010101001",
                        Address(
                            "24 rue yves montand",
                            null,
                            "Bourges",
                            "Centre",
                            "18000",
                            "France"
                        )
                    )
                ),
                PetAnimal(
                    type = "dog",
                    breed = "labrador",
                    color = "brown",
                    age = "young",
                    gender = "male",
                    size = "meduim",
                    name = "rex",
                    description = "description du chien",
                    environment = null,
                    photos = emptyList(),
                    contact = null
                )
            )
        )

        // THEN
        assert(
            viewModel.liveData.value == PetFinderViewModel(
                state = PetFinderViewModelState.finished,
                animals = listOf(
                    PetAnimalViewModel(
                        name = "rex",
                        description = "description du chien",
                        photos = listOf(PhotoViewModel(small = "pictureSmall")),
                        shouldShowPhoto = true
                    ),
                    PetAnimalViewModel(
                        name = "rex",
                        description = "description du chien",
                        photos = emptyList(),
                        shouldShowPhoto = false
                    )
                )
            )
        )
    }

    @Test
    fun presentLoader() {
        //WHEN
        presenter.presentLoader()
        //THEN
        assert(
            viewModel.liveData.value == PetFinderViewModel(
                state = PetFinderViewModelState.loading,
                animals = emptyList()
            )
        )
    }

    @Test
    fun presentError() {
        //WHEN
        presenter.presentError()
        //THEN
        assert(
            viewModel.liveData.value == PetFinderViewModel(
                state = PetFinderViewModelState.error,
                animals = emptyList()
            )
        )
    }
}