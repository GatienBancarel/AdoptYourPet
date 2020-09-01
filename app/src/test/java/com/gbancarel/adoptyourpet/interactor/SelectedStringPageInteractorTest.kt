package com.gbancarel.adoptyourpet.interactor

import com.gbancarel.adoptyourpet.interactor.data.listBreeds.Breeds
import com.gbancarel.adoptyourpet.interactor.data.listColors.Colors
import com.gbancarel.adoptyourpet.presenter.SelectedStringPagePresenter
import com.gbancarel.adoptyourpet.repository.ListBreedsRepository
import com.gbancarel.adoptyourpet.repository.ListColorsRepository
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.then
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class SelectedStringPageInteractorTest {

    @Mock private lateinit var breedsRepository: ListBreedsRepository
    @Mock private lateinit var colorsRepository: ListColorsRepository
    @Mock private lateinit var presenter: SelectedStringPagePresenter
    private lateinit var interactor: SelectedStringPageInteractor


    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        interactor = SelectedStringPageInteractor(breedsRepository, colorsRepository, presenter)
    }

    @Test
    fun updateListBreeds_WhenTitleIsBreeds() {
        val breeds :List<Breeds> = listOf(
            Breeds("labrador"),
            Breeds("caniche"),
            Breeds("staff")
        )
        //GIVEN
        given(breedsRepository.getListBreedsLocal()).willReturn(breeds)
        //WHEN
        interactor.updateListString(listOf("labrador","caniche"), "breeds")
        //THEN
        then(presenter).should().presentBreeds(breeds, listOf("labrador","caniche"))

    }

    @Test
    fun updateListBreeds_WhenTitleIsColors() {
        val colors :List<Colors> = listOf(
            Colors("black"),
            Colors("white"),
        )
        //GIVEN
        given(colorsRepository.getListColorsLocal()).willReturn(colors)
        //WHEN
        interactor.updateListString(listOf("black"), "colors")
        //THEN
        then(presenter).should().presentColors(colors, listOf("black"))
    }

    @Test
    fun updateBreed() {
        //WHEN
        interactor.updateBreed("labrador",true)
        //THEN
        presenter.present("labrador",true)
    }
}