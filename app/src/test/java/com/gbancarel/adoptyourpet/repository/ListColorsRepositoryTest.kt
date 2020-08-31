package com.gbancarel.adoptyourpet.repository

import com.gbancarel.adoptyourpet.repository.dao.ColorsDao
import com.gbancarel.adoptyourpet.repository.parser.ColorsParser
import com.gbancarel.adoptyourpet.repository.service.PetFinderService
import org.junit.Test

import org.junit.Assert.*
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
    fun loadColors() {
        // TODO GBA
        // GIVEN
        // WHEN
        // THEN
    }
}