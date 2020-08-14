package com.gbancarel.adoptyourpet.presenter

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.gbancarel.adoptyourpet.interactor.donnes.PetAnimal
import com.gbancarel.adoptyourpet.interactor.donnes.PetFinder
import com.gbancarel.adoptyourpet.presenter.donnees.PetAnimalViewModel
import com.gbancarel.adoptyourpet.presenter.donnees.PetFinderViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class MyPresenterTest {
    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @Mock
    private lateinit var context: Context
    private var viewModel: MyViewModel = MyViewModel()
    private lateinit var presenter: MyPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = MyPresenter(viewModel, context)
    }

    @Test
    fun present() {
        // WHEN
        presenter.present(
                PetFinder(
                        listOf(
                                PetAnimal(
                                        type = "dog",
                                        age = "young",
                                        gender = "male",
                                        size = "meduim",
                                        name = "rex",
                                        description = "description du chien"),
                                PetAnimal(
                                        type = "dog",
                                        age = "young",
                                        gender = "male",
                                        size = "meduim",
                                        name = "rex",
                                        description = "description du chien")
                        )
                )
        )

        // THEN
        assert(
                viewModel.liveData.value == PetFinderViewModel(
                        listOf(
                                PetAnimalViewModel(
                                        type = "dog",
                                        age = "young",
                                        gender = "male",
                                        size = "meduim",
                                        name = "rex",
                                        description = "description du chien"
                                ),
                                PetAnimalViewModel(
                                        type = "dog",
                                        age = "young",
                                        gender = "male",
                                        size = "meduim",
                                        name = "rex",
                                        description = "description du chien"
                                )
                        )
                )
        )
    }
}