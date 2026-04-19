package com.example.nowlocationn.repository

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchRepositorio @Inject constructor() {

    // Simulación de una base de datos más amplia
    private val todasLasCiudades = listOf(
        "Málaga", "Madrid", "Granada", "Barcelona", "Mallorca",
        "Valencia", "Sevilla", "Bilbao", "Zaragoza", "Alicante",
        "Murcia", "Córdoba", "Vigo", "Gijón"
    )

    // El repositorio ahora devuelve un Flow o simplemente filtra eficientemente
    fun getSugerencias(query: String): List<String> {
        if (query.length < 2) return emptyList()

        return todasLasCiudades.filter {
            it.contains(query, ignoreCase = true)
        }.sorted() // Ordenar alfabéticamente queda mucho más profesional
    }
}