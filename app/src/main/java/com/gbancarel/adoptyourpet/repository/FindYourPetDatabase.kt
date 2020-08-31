package com.gbancarel.adoptyourpet.repository

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gbancarel.adoptyourpet.repository.dao.AnimalDao
import com.gbancarel.adoptyourpet.repository.dao.BreedDao
import com.gbancarel.adoptyourpet.repository.local.*

@Database(entities = [
    PetAnimalLocal::class,
    BreedLocal::class
], version = 2)
abstract class FindYourPetDatabase : RoomDatabase() {
    abstract fun animalDao(): AnimalDao
    abstract fun breedDao(): BreedDao
}