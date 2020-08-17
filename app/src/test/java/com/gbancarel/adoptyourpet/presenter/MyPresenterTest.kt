package com.gbancarel.adoptyourpet.presenter

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.gbancarel.adoptyourpet.interactor.donnes.*
import com.gbancarel.adoptyourpet.presenter.donnees.PetAnimalViewModel
import com.gbancarel.adoptyourpet.presenter.donnees.PhotoViewModel
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
            viewModel.liveData.value ==
                listOf(
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
    }
}