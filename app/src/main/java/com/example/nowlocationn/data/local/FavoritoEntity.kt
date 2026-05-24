package com.example.nowlocationn.data.local


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favoritos")
data class FavoritoEntity(
    @PrimaryKey
    val nombre: String,
    val tipo: String,
    val descripcion: String,
    val lat: Double?,
    val lon: Double?
)