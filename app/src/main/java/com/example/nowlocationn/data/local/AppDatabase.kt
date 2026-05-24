package com.example.nowlocationn.data.local

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(
    entities = [
        FavoritoEntity::class,
        HistorialEntity::class
    ],
    version = 2
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoritoDao(): FavoritoDao
    abstract fun historialDao(): HistorialDao
}