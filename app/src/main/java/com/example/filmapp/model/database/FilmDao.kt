package com.example.filmapp.model.database

import androidx.room.*

@Dao
interface FilmDao {
    @Query("SELECT * FROM FilmEntity")
    fun all(): List<FilmEntity>

    @Query("SELECT * FROM FilmEntity WHERE idFilm LIKE :id")
    fun getDataById(id: String): List<FilmEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(entity: FilmEntity)

    @Update
    fun update(entity: FilmEntity)

    @Delete
    fun delete(entity: FilmEntity)
}