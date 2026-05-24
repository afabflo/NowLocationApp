package com.example.nowlocationn.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nowlocationn.repository.HistorialRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistorialViewModel @Inject constructor(
    private val repository: HistorialRepository
) : ViewModel() {

    val historial = repository.historial

    fun guardarCiudad(ciudad: String) {
        viewModelScope.launch {
            repository.guardarCiudad(ciudad)
        }
    }

    fun borrarHistorial() {
        viewModelScope.launch {
            repository.borrarHistorial()
        }
    }
}