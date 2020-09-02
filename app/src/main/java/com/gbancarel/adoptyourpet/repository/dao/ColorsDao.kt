package com.gbancarel.adoptyourpet.repository.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.gbancarel.adoptyourpet.repository.local.ColorsLocal

@Dao
interface ColorsDao {
    @Query("SELECT * FROM colorslocal")
    fun getAll(): List<ColorsLocal>

    @Insert
    fun insertAll(colors: List<ColorsLocal>)

    @Query("DELETE FROM colorslocal")
    fun deleteAll()
}