package com.example.nowlocationn.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nowlocationn.model.EventoDto
import com.example.nowlocationn.repository.EventosRepositorio
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventosViewModel @Inject constructor(
    private val repositorio: EventosRepositorio
) : ViewModel() {

    private val _eventos = MutableStateFlow<List<EventoDto>>(emptyList())
    val eventos: StateFlow<List<EventoDto>> = _eventos.asStateFlow()

    private val _cargando = MutableStateFlow(false)
    val cargando: StateFlow<Boolean> = _cargando.asStateFlow()

    fun buscarEventos(ciudad: String) {
        viewModelScope.launch {
            _cargando.value = true
            _eventos.value = repositorio.buscarEventos(ciudad)
            _cargando.value = false
        }
    }
}