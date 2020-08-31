package com.gbancarel.adoptyourpet.repository

import com.gbancarel.adoptyourpet.interactor.data.Size
import com.gbancarel.adoptyourpet.state.AnimalSelected
import org.junit.Assert.assertEquals
import org.junit.Test

class ListSizeRepositoryTest {

    private val repository = ListSizeRepository()

    @Test
    fun getListSizeDog() {
        assertEquals(
            repository.getListSize(AnimalSelected.dog),
                listOf(
                    Size(0, "Small (0-25 lbs)"),
                    Size(1, "Medium (26-60 lbs)"),
                    Size(2, "Large (61-100 lbs)"),
                    Size(3, "Extra Large (101 lbs or more)")
                )
            )
    }

    @Test
    fun getListSizeCat() {
        assertEquals(
            repository.getListSize(AnimalSelected.cat),
                listOf(
                    Size(4, "Small (0-6 lbs)"),
                    Size(5, "Medium (7-11 lbs)"),
                    Size(6, "Large (12-16 lbs)"),
                    Size(7, "Extra Large (17 lbs or more)")
                )
            )
    }
}