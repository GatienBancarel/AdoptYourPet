package com.gbancarel.adoptyourpet.repository

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gbancarel.adoptyourpet.repository.dao.AnimalDao
import com.gbancarel.adoptyourpet.repository.dao.BreedDao
import com.gbancarel.adoptyourpet.repository.dao.ColorsDao
import com.gbancarel.adoptyourpet.repository.local.BreedLocal
import com.gbancarel.adoptyourpet.repository.local.ColorsLocal
import com.gbancarel.adoptyourpet.repository.local.PetAnimalLocal

@Database(entities = [
    PetAnimalLocal::class,
    BreedLocal::class,
    ColorsLocal::class
], version = 3)
abstract class FindYourPetDatabase : RoomDatabase() {
    abstract fun animalDao(): AnimalDao
    abstract fun breedDao(): BreedDao
    abstract fun colorsDao(): ColorsDao
}