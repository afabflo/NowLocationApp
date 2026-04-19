package com.example.nowlocationn.viewmodel

import androidx.lifecycle.ViewModel
import com.example.nowlocationn.repository.SearchRepositorio
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repositorio: SearchRepositorio
) : ViewModel() {

    // Estado del texto de búsqueda
    private val _lugar = MutableStateFlow("")
    val lugar: StateFlow<String> = _lugar.asStateFlow()

    // Estado de las sugerencias
    private val _sugerencias = MutableStateFlow<List<String>>(emptyList())
    val sugerencias: StateFlow<List<String>> = _sugerencias.asStateFlow()

    fun onLugarChange(nuevoLugar: String) {
        _lugar.value = nuevoLugar

        _sugerencias.value = repositorio.getSugerencias(nuevoLugar)
    }
}