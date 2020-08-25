package com.gbancarel.adoptyourpet.repository.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.gbancarel.adoptyourpet.repository.local.BreedLocal

@Dao
interface BreedDao {
    @Query("SELECT * FROM breedlocal")
    fun getAll(): List<BreedLocal>

    @Insert
    fun insertAll(breeds: List<BreedLocal>)
}