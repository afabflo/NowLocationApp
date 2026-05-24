package com.example.nowlocationn.repository


import com.example.nowlocationn.data.local.FavoritoDao
import com.example.nowlocationn.data.local.FavoritoEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoritosRepository @Inject constructor(
    private val dao: FavoritoDao
) {

    fun obtenerFavoritos(): Flow<List<FavoritoEntity>> =
        dao.obtenerFavoritos()

    suspend fun guardarFavorito(favorito: FavoritoEntity) {
        dao.insertarFavorito(favorito)
    }

    suspend fun eliminarFavorito(favorito: FavoritoEntity) {
        dao.eliminarFavorito(favorito)
    }

    suspend fun esFavorito(nombre: String): Boolean {
        return dao.esFavorito(nombre)
    }
}