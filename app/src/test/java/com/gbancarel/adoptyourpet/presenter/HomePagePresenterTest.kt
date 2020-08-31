package com.gbancarel.adoptyourpet.presenter

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.gbancarel.adoptyourpet.R
import com.gbancarel.adoptyourpet.interactor.data.listAnimal.*
import com.gbancarel.adoptyourpet.presenter.data.listAnimal.*
import junit.framework.Assert.assertEquals
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
    private val animals = listOf(
        PetAnimal(
            type = "dog",
            breed = "labrador",
            color = "brown",
            age = "young",
            gender = "male",
            size = "medium",
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

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = HomePagePresenter(viewModel, context)
    }

    @Test
    fun present() {
        // WHEN
        presenter.present(animals)

        // THEN
        assertEquals(
            viewModel.liveData.value, PetFinderViewModelData(
                state = PetFinderViewModelState.finished,
                animals = listOf(
                    PetAnimalViewModel(
                        name = "rex",
                        description = "description du chien",
                        photos = listOf(PhotoViewModel(medium = "pictureMedium")),
                        shouldShowPhoto = true
                    ),
                    PetAnimalViewModel(
                        name = "rex",
                        description = "description du chien",
                        photos = emptyList(),
                        shouldShowPhoto = false
                    )
                ),
                detailAnimal = null
            )
        )
    }

    @Test
    fun presentWithRequestedAnimal() {
        // WHEN
        presenter.present(
            animals,
            PetAnimal(
                type = "dog",
                breed = "labrador",
                color = "brown",
                age = "young",
                gender = "male",
                size = "medium",
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
            )
        )

        // THEN
        assertEquals(
            viewModel.liveData.value, PetFinderViewModelData(
                state = PetFinderViewModelState.finished,
                animals = listOf(
                    PetAnimalViewModel(
                        name = "rex",
                        description = "description du chien",
                        photos = listOf(PhotoViewModel(medium = "pictureMedium")),
                        shouldShowPhoto = true
                    ),
                    PetAnimalViewModel(
                        name = "rex",
                        description = "description du chien",
                        photos = emptyList(),
                        shouldShowPhoto = false
                    )
                ),
                detailAnimal = PetDetailViewModelData(
                    "rex",
                    R.drawable.young,
                    R.mipmap.dog,
                    "labrador",
                    "brown",
                    R.drawable.medium,
                    R.drawable.male,
                    "description du chien",
                    "pictureMedium"
                )
            )
        )
    }

    @Test
    fun presentWithRequestedAnimalWithNullColor() {
        // WHEN
        presenter.present(
            animals,
            PetAnimal(
                type = "dog",
                breed = "labrador",
                color = null,
                age = "young",
                gender = "male",
                size = "medium",
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
            )
        )

        // THEN
        assertEquals(
            viewModel.liveData.value, PetFinderViewModelData(
                state = PetFinderViewModelState.finished,
                animals = listOf(
                    PetAnimalViewModel(
                        name = "rex",
                        description = "description du chien",
                        photos = listOf(PhotoViewModel(medium = "pictureMedium")),
                        shouldShowPhoto = true
                    ),
                    PetAnimalViewModel(
                        name = "rex",
                        description = "description du chien",
                        photos = emptyList(),
                        shouldShowPhoto = false
                    )
                ),
                detailAnimal = PetDetailViewModelData(
                    "rex",
                    R.drawable.young,
                    R.mipmap.dog,
                    "labrador",
                    "",
                    R.drawable.medium,
                    R.drawable.male,
                    "description du chien",
                    "pictureMedium"
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
            viewModel.liveData.value == PetFinderViewModelData(
                state = PetFinderViewModelState.loading,
                animals = emptyList(),
                detailAnimal = null
            )
        )
    }

    @Test
    fun presentError() {
        //WHEN
        presenter.presentError()
        //THEN
        assert(
            viewModel.liveData.value == PetFinderViewModelData(
                state = PetFinderViewModelState.error,
                animals = emptyList(),
                detailAnimal = null
            )
        )
    }
}