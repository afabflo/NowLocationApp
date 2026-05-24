package com.example.nowlocationn.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nowlocationn.model.Lugar
import com.example.nowlocationn.repository.SearchRepositorio
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetallesViewModel @Inject constructor(
    private val searchRepositorio: SearchRepositorio
) : ViewModel() {
    private var ultimaBusqueda: String? = null

    private val _lugares = MutableStateFlow<List<Lugar>>(emptyList())
    val lugares: StateFlow<List<Lugar>> = _lugares.asStateFlow()

    private val _cargando = MutableStateFlow(false)
    val cargando: StateFlow<Boolean> = _cargando.asStateFlow()

    fun cargarPlanes(ciudad: String, categoria: String) {
        val clave = "$ciudad-$categoria"

        if (ultimaBusqueda == clave && _lugares.value.isNotEmpty()) {
            return
        }

        ultimaBusqueda = clave
        viewModelScope.launch {
            try {
                _cargando.value = true
                _lugares.value = emptyList()

                val tipoPlan = when (categoria) {
                    "Comer" -> "COMER"
                    "Noche" -> "FIESTA"
                    "Visitar" -> "VISITAR"
                    "Relax" -> "RELAX"
                    else -> "FIESTA"
                }

                val lugaresEncontrados = searchRepositorio.obtenerCordenadas(
                    ciudad = ciudad,
                    tipoPlan = tipoPlan
                )

                _lugares.value = lugaresEncontrados

                android.util.Log.d("PLANES_DEBUG", lugaresEncontrados.toString())

            } catch (e: Exception) {
                android.util.Log.e("PLANES_DEBUG", "Error cargando planes", e)
                _lugares.value = emptyList()
            } finally {
                _cargando.value = false
            }
        }
    }
}