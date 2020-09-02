package com.gbancarel.adoptyourpet.repository

import com.gbancarel.adoptyourpet.interactor.data.listColors.Colors
import com.gbancarel.adoptyourpet.repository.dao.ColorsDao
import com.gbancarel.adoptyourpet.repository.error.CannotDecodeJsonException
import com.gbancarel.adoptyourpet.repository.error.ErrorStatusException
import com.gbancarel.adoptyourpet.repository.json.listColors.ColorsJSON
import com.gbancarel.adoptyourpet.repository.json.listColors.TypeJSON
import com.gbancarel.adoptyourpet.repository.local.ColorsLocal
import com.gbancarel.adoptyourpet.repository.parser.ColorsParser
import com.gbancarel.adoptyourpet.repository.service.PetFinderService
import com.gbancarel.adoptyourpet.repository.service.ResponseRequest
import com.gbancarel.adoptyourpet.state.AnimalSelected
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.then
import org.junit.Assert
import org.junit.Test
import org.junit.Before
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class ListColorsRepositoryTest {
    @Mock
    private lateinit var service: PetFinderService
    @Mock
    private lateinit var parser: ColorsParser
    @Mock
    private lateinit var dao: ColorsDao
    private lateinit var repository: ListColorsRepository

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        repository = ListColorsRepository(service, parser, dao)
    }

    @Test
    fun loadColors_whenGetCallSuccess() {
        // GIVEN
        given(
            service.get("https://api.petfinder.com/v2/types/dog")
        )
            .willReturn(
                ResponseRequest(
                    200,
                    "body200"
                )
            )
        given(
            parser.parse(
                "body200"
            )
        )
            .willReturn(
                TypeJSON(
                    type = ColorsJSON(listOf("black", "white"))
                )
            )
        // WHEN
        repository.loadColors(AnimalSelected.dog)
        // THEN
        then(dao.deleteAll())
        then(
            dao.insertAll(
                listOf(
                    ColorsLocal(primary = "black"),
                    ColorsLocal(primary = "white")
                )
            )
        )
    }

    @Test(expected = ErrorStatusException::class)
    fun loadColors_whenGetCallWrongStatus() {
        // GIVEN
        given(
            service.get(
                "https://api.petfinder.com/v2/types/dog"
            )
        ).willReturn(
            ResponseRequest(
                401,
                "body401"
            )
        )

        // WHEN
        repository.loadColors(AnimalSelected.dog)
    }

    @Test(expected = CannotDecodeJsonException::class)
    fun loadColors_whenGetCallCannotDecodeJson() {
        // GIVEN
        given(
            service.get(
                "https://api.petfinder.com/v2/types/dog"
            )
        ).willReturn(
            ResponseRequest(
                200,
                "body200"
            )
        )
        given(
            parser.parse(
                "body200"
            )
        ).willReturn(null)

        // WHEN
        repository.loadColors(AnimalSelected.dog)
    }

    @Test
    fun getListColorsLocalTest() {
        //GIVEN
        given(
            dao.getAll()
        ).willReturn(
            listOf(
                ColorsLocal(id = 0, primary = "black"),
                ColorsLocal(id = 0, primary = "white")
            )
        )
        //WHEN
        val call = repository.getListColorsLocal()
        //THEN
        Assert.assertEquals(
            call,
            listOf(
                Colors(primary = "black"),
                Colors(primary = "white")
            )
        )
    }
}