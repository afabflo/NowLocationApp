package com.example.nowlocationn.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nowlocationn.data.local.FavoritoEntity
import com.example.nowlocationn.model.Lugar
import com.example.nowlocationn.repository.FavoritosRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritosViewModel @Inject constructor(
    private val favoritosRepository: FavoritosRepository
) : ViewModel() {

    private val _esFavorito = MutableStateFlow(false)
    val esFavorito: StateFlow<Boolean> = _esFavorito.asStateFlow()
    val favoritos = favoritosRepository.obtenerFavoritos()
    fun comprobarFavorito(nombre: String) {
        viewModelScope.launch {
            _esFavorito.value = favoritosRepository.esFavorito(nombre)
        }
    }
    fun eliminarFavorito(favorito: FavoritoEntity) {
        viewModelScope.launch {
            favoritosRepository.eliminarFavorito(favorito)
        }
    }
    fun cambiarFavorito(lugar: Lugar) {
        viewModelScope.launch {
            val favorito = FavoritoEntity(
                nombre = lugar.nombre,
                tipo = lugar.tipo,
                descripcion = lugar.descripcion,
                lat = lugar.lat,
                lon = lugar.lon
            )

            if (_esFavorito.value) {
                favoritosRepository.eliminarFavorito(favorito)
                _esFavorito.value = false
            } else {
                favoritosRepository.guardarFavorito(favorito)
                _esFavorito.value = true
            }
        }
    }
}