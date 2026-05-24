package com.example.nowlocationn.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface HistorialDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarBusqueda(historial: HistorialEntity)

    @Query("SELECT * FROM historial_busquedas ORDER BY fecha DESC LIMIT 6")
    fun obtenerHistorial(): Flow<List<HistorialEntity>>

    @Query("DELETE FROM historial_busquedas")
    suspend fun borrarHistorial()
}