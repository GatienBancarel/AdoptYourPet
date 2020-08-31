package com.gbancarel.adoptyourpet.interactor

import com.gbancarel.adoptyourpet.interactor.data.listBreeds.Breeds
import com.gbancarel.adoptyourpet.presenter.SelectedStringPagePresenter
import com.gbancarel.adoptyourpet.repository.ListBreedsRepository
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.then
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class BreedsPageInteractorTest {

    @Mock private lateinit var repository: ListBreedsRepository
    @Mock private lateinit var presenter: SelectedStringPagePresenter
    private lateinit var interactor: SelectedStringPageInteractor


    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        interactor = SelectedStringPageInteractor(repository, presenter)
    }

    @Test
    fun updateListBreeds() {
        val breeds :List<Breeds> = listOf(
            Breeds("labrador"),
            Breeds("caniche"),
            Breeds("staff")
        )
        //GIVEN
        given(repository.getListBreedsLocal()).willReturn(breeds)
        //WHEN
        interactor.updateListBreeds(listOf("labrador","caniche"))
        //THEN
        then(presenter).should().present(breeds, listOf("labrador","caniche"))

    }

    @Test
    fun updateBreed() {
        //WHEN
        interactor.updateBreed("labrador",true)
        //THEN
        presenter.present("labrador",true)
    }
}