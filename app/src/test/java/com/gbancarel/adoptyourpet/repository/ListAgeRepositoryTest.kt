package com.gbancarel.adoptyourpet.repository

import androidx.compose.runtime.referentialEqualityPolicy
import com.gbancarel.adoptyourpet.interactor.data.Age
import org.junit.Test

import org.junit.Assert.*

class ListAgeRepositoryTest {

    private val ageRepository = ListAgeRepository()

    @Test
    fun getListAge() {
        assertEquals(
            ageRepository.getListAge(), listOf(
                Age(0, "Kitten"),
                Age(1, "Young"),
                Age(2, "Adult"),
                Age(3, "Senior")
            )
        )
    }
}