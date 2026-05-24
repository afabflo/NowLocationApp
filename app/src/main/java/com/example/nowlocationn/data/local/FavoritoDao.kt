package com.example.nowlocationn.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface FavoritoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarFavorito(favoritoEntity: FavoritoEntity)
    @Delete
    suspend fun  eliminarFavorito(favoritoEntity: FavoritoEntity)
    @Query("SELECT * FROM favoritos")
    fun obtenerFavoritos(): Flow<List<FavoritoEntity>>
    @Query("SELECT EXISTS(SELECT 1 FROM favoritos WHERE nombre = :nombre)")
    suspend fun esFavorito(nombre:String) : Boolean

}