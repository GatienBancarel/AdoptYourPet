package com.gbancarel.adoptyourpet.interactor

import com.gbancarel.adoptyourpet.presenter.SearchPagePresenter
import com.gbancarel.adoptyourpet.repository.ListBreedsRepository
import com.gbancarel.adoptyourpet.repository.error.CannotDecodeJsonException
import com.gbancarel.adoptyourpet.repository.error.ErrorStatusException
import com.gbancarel.adoptyourpet.repository.error.NoInternetConnectionAvailable
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.then
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class SearchPageInteractorTest {
    @Mock private lateinit var repository: ListBreedsRepository
    @Mock private lateinit var presenter: SearchPagePresenter
    private lateinit var interactor: SearchPageInteractor


    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        interactor = SearchPageInteractor(repository, presenter)
    }

    @Test
    fun getCallSucces() {
        //GIVEN
        //WHEN
        interactor.getListBreeds("dog")
        //THEN
        then(presenter).should().presentLoader()
        then(repository).should().getListBreeds("dog")
        then(presenter).should().present()
    }

    @Test
    fun getCallWhenErrorCannotDecodeJsonException() {
        // GIVEN
        given(repository.getListBreeds("dog")).willThrow(CannotDecodeJsonException("Fake reason"))
        // WHEN
        interactor.getListBreeds("dog")
        // THEN
        then(presenter).should().presentError()
    }

    @Test
    fun getCallWhenErrorStatusException() {
        // GIVEN
        given(repository.getListBreeds("dog")).willThrow(ErrorStatusException("Fake reason"))
        // WHEN
        interactor.getListBreeds("dog")
        // THEN
        then(presenter).should().presentError()
    }

    @Test
    fun getCallWhenNoInternetConnectionAvailable() {
        // GIVEN
        given(repository.getListBreeds("dog")).willThrow(NoInternetConnectionAvailable("Fake reason"))
        // WHEN
        interactor.getListBreeds("dog")
        // THEN
        then(presenter).should().presentError()
    }

}