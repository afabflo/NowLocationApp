package com.example.nowlocationn.repository

import com.example.nowlocationn.data.local.HistorialDao
import com.example.nowlocationn.data.local.HistorialEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HistorialRepository @Inject constructor(
    private val dao: HistorialDao
) {
    val historial = dao.obtenerHistorial()

    suspend fun guardarCiudad(ciudad: String) {
        dao.insertarBusqueda(
            HistorialEntity(ciudad = ciudad)
        )
    }

    suspend fun borrarHistorial() {
        dao.borrarHistorial()
    }
}