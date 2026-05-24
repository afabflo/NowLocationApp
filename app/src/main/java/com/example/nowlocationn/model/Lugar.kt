package com.example.nowlocationn.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Lugar(
    val nombre: String,
    val tipo: String,
    val descripcion: String,
    val lat: Double?,
    val lon: Double?,
    val puntuacion: Double = 0.0,
    val fotoUrl: String? = null,
    val distanciaKm: Double? = null
) : Parcelable