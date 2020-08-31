package com.gbancarel.adoptyourpet.module

import android.content.Context
import androidx.room.Room
import com.gbancarel.adoptyourpet.repository.FindYourPetDatabase
import com.gbancarel.adoptyourpet.repository.dao.AnimalDao
import com.gbancarel.adoptyourpet.repository.dao.BreedDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
object DatabaseModule {
    private val FINDYOURPET_DATABASE_NAME = "findYourPetDatabase"

    @Singleton
    @Provides
    fun provideFindYourPetDatabase(
        @ApplicationContext context: Context
    ): FindYourPetDatabase =
        Room.databaseBuilder(context, FindYourPetDatabase::class.java, FINDYOURPET_DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideBreedDao(
        db: FindYourPetDatabase
    ) : BreedDao  = db.breedDao()

    @Provides
    fun provideAnimalDao(
        db: FindYourPetDatabase
    ) : AnimalDao = db.animalDao()

}