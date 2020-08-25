package com.gbancarel.adoptyourpet.repository

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gbancarel.adoptyourpet.repository.dao.BreedDao
import com.gbancarel.adoptyourpet.repository.local.BreedLocal

@Database(entities = [BreedLocal::class], version = 1)
abstract class FindYourPetDatabase : RoomDatabase() {
    abstract fun breedDao(): BreedDao
}