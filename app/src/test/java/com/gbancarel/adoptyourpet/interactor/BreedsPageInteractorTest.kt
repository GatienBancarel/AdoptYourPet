package com.gbancarel.adoptyourpet.interactor

import com.gbancarel.adoptyourpet.interactor.data.listBreeds.Breeds
import com.gbancarel.adoptyourpet.presenter.BreedsPagePresenter
import com.gbancarel.adoptyourpet.repository.ListBreedsRepository
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.then
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class BreedsPageInteractorTest {

    @Mock private lateinit var repository: ListBreedsRepository
    @Mock private lateinit var presenter: BreedsPagePresenter
    private lateinit var interactor: BreedsPageInteractor


    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        interactor = BreedsPageInteractor(repository, presenter)
    }

    @Test
    fun getCallSuccess() {
        val breeds :List<Breeds> = listOf(
            Breeds("labrador"),
            Breeds("caniche")
        )
        //GIVEN
        given(repository.getListBreedsLocal()).willReturn(breeds)
        //WHEN
        interactor.getListBreeds()
        //THEN
        then(presenter).should().present(breeds)

    }
}