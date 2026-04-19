package com.example.nowlocationn.viewmodel


import androidx.lifecycle.ViewModel
import com.example.nowlocationn.model.Lugar
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class DetallesViewModel : ViewModel() {

    // Simulación de datos (esto en el futuro será tu Repositorio)
    private val todosLosLugares = listOf(
        Lugar("Restaurante El Pimpi", "Bodega emblemática de Málaga.", "Comer", "Málaga", 4.8),
        Lugar("Discoteca Mae West", "La mejor fiesta de Granada.", "Noche", "Granada", 4.5),
        Lugar("La Alcazaba", "Fortificación palaciega de época islámica.", "Visitar", "Málaga", 4.9)
    )

    private val _lugares = MutableStateFlow<List<Lugar>>(emptyList())
    val lugares: StateFlow<List<Lugar>> = _lugares.asStateFlow()

    fun filtrarLugares(ciudad: String, categoria: String) {
        _lugares.value = todosLosLugares.filter {
            it.ciudad.equals(ciudad, ignoreCase = true) &&
                    it.categoria.equals(categoria, ignoreCase = true)
        }
    }
}