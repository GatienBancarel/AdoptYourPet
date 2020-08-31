package com.gbancarel.adoptyourpet.repository.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.gbancarel.adoptyourpet.repository.local.PetAnimalLocal

@Dao
interface AnimalDao {
    @Query("SELECT * FROM animals")
    fun getAll(): List<PetAnimalLocal>

    @Insert
    fun insertAll(animals: List<PetAnimalLocal>)

    @Query("DELETE FROM animals")
    fun deleteAll()
}