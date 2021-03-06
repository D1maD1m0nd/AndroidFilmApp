package com.example.filmapp.model.database

import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.filmapp.framework.App

@androidx.room.Database(
    entities = [FilmEntity::class],
    version = 1,
    exportSchema = false
)
abstract class Database : RoomDatabase() {
    abstract fun filmDao(): FilmDao

    companion object {
        private const val DB_NAME = "add_data_base2.db"
        val db: Database by lazy {
            Room.databaseBuilder(
                App.appInstance,
                Database::class.java,
                DB_NAME
            ).build()
        }
    }

}