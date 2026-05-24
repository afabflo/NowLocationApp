package com.example.nowlocationn.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "historial_busquedas")
data class HistorialEntity(
    @PrimaryKey
    val ciudad: String,
    val fecha: Long = System.currentTimeMillis()
)