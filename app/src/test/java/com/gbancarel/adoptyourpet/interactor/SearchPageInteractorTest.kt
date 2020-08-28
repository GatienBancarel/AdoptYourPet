package com.gbancarel.adoptyourpet.interactor

import com.gbancarel.adoptyourpet.interactor.data.Size
import com.gbancarel.adoptyourpet.presenter.SearchPagePresenter
import com.gbancarel.adoptyourpet.repository.ListBreedsRepository
import com.gbancarel.adoptyourpet.repository.ListSizeRepository
import com.gbancarel.adoptyourpet.repository.error.CannotDecodeJsonException
import com.gbancarel.adoptyourpet.repository.error.ErrorStatusException
import com.gbancarel.adoptyourpet.repository.error.NoInternetConnectionAvailable
import com.gbancarel.adoptyourpet.state.AnimalSelected
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.then
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class SearchPageInteractorTest {
    @Mock private lateinit var breedsRepository: ListBreedsRepository
    @Mock private lateinit var sizeRepository: ListSizeRepository
    @Mock private lateinit var presenter: SearchPagePresenter
    private lateinit var interactor: SearchPageInteractor


    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        interactor = SearchPageInteractor(breedsRepository, sizeRepository, presenter)
    }

    @Test
    fun loadSuccess() {
        //GIVEN
        given(sizeRepository.getListSize(AnimalSelected.dog)).willReturn(
            listOf(
                Size(0, "Small")
            )
        )
        //WHEN
        interactor.load(AnimalSelected.dog)

        //THEN
        then(presenter).should().presentBreedsLoader()
        then(presenter).should().presentSizes(listOf(
            Size(0, "Small")
        ))
        then(breedsRepository).should().loadBreeds(AnimalSelected.dog)
        then(presenter).should().presentBreedsLoaderFinished()
    }

    @Test
    fun loadWhenErrorCannotDecodeJsonException() {
        // GIVEN
        given(breedsRepository.loadBreeds(AnimalSelected.dog)).willThrow(CannotDecodeJsonException("Fake reason"))
        // WHEN
        interactor.load(AnimalSelected.dog)
        // THEN
        then(presenter).should().presentError()
    }

    @Test
    fun loadCallWhenErrorStatusException() {
        // GIVEN
        given(breedsRepository.loadBreeds(AnimalSelected.dog)).willThrow(ErrorStatusException("Fake reason"))
        // WHEN
        interactor.load(AnimalSelected.dog)
        // THEN
        then(presenter).should().presentError()
    }

    @Test
    fun getCallWhenNoInternetConnectionAvailable() {
        // GIVEN
        given(breedsRepository.loadBreeds(AnimalSelected.dog)).willThrow(NoInternetConnectionAvailable("Fake reason"))
        // WHEN
        interactor.load(AnimalSelected.dog)
        // THEN
        then(presenter).should().presentError()
    }

    @Test
    fun selectedBreeds() {
        //WHEN
        interactor.selectBreeds(listOf("labrador"))
        //THEN
        presenter.presentSelectBreeds(listOf("labrador"))
    }

    @Test
    fun selectedSize() {
        //WHEN
        interactor.selectedSize(0)
        //THEN
        presenter.presentSelectedNewSize(0)
    }

    @Test
    fun selectedAge() {
        //WHEN
        interactor.selectedAge("Young", true, 1)
        //THEN
        presenter.presentAge("Young",true, 1)
    }


}