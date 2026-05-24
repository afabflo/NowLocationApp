package com.example.nowlocationn.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nowlocationn.repository.SearchRepositorio
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repositorio: SearchRepositorio
) : ViewModel() {

    private val _sugerencias = MutableStateFlow<List<String>>(emptyList())
    val sugerencias: StateFlow<List<String>> = _sugerencias.asStateFlow()

    fun buscarMunicipios(query: String) {
        viewModelScope.launch {
            repositorio.getSugerencias(query).collect { resultados ->
                _sugerencias.value = resultados
            }
        }
    }
}