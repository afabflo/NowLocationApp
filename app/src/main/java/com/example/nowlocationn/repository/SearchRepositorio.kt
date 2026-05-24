package com.example.nowlocationn.repository

import android.util.Log
import com.example.nowlocationn.interfaces.GeoApiService
import com.example.nowlocationn.interfaces.NominatimApiService
import com.example.nowlocationn.interfaces.OverpassApiService
import com.example.nowlocationn.model.Lugar
import com.example.nowlocationn.utils.calcularDistanciaKm
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchRepositorio @Inject constructor(
    private val apiService: GeoApiService,
    private val apiNominatim: NominatimApiService,
    private val apiOverpass: OverpassApiService
) {

    private var municipiosCache: List<String>? = null
    private val lugaresCache = mutableMapOf<String, List<Lugar>>()

    suspend fun obtenerCordenadas(
        ciudad: String,
        tipoPlan: String = "FIESTA"
    ): List<Lugar> {

        val cacheKey = "${ciudad.trim().lowercase()}_${tipoPlan.uppercase()}"

        lugaresCache[cacheKey]?.let { lugares ->
            Log.d("API_DEBUG", "Lugares desde caché: $cacheKey")
            return lugares
        }

        return try {
            val respuesta = apiNominatim.buscarCiudad(ciudad)

            if (respuesta.isEmpty()) {
                Log.d("API_DEBUG", "No se encontraron coordenadas para $ciudad")
                return emptyList()
            }

            val lat = respuesta[0].lat
            val lon = respuesta[0].lon

            Log.d("API_DEBUG", "¡CONSEGUIDO! $ciudad: Lat $lat, Lon $lon")

            val lugares = buscarPlanesPorCategoria(
                lat = lat,
                lon = lon,
                tipoPlan = tipoPlan
            )

            if (lugares.isNotEmpty()) {
                lugaresCache[cacheKey] = lugares
            } else {
                Log.d("API_DEBUG", "No guardo caché vacía para $cacheKey")
            }

            lugares

        } catch (e: Exception) {
            Log.e("API_DEBUG", "Error en Nominatim: ${e.message}", e)
            emptyList()
        }
    }

    suspend fun buscarPlanesPorCategoria(
        lat: String,
        lon: String,
        tipoPlan: String = "FIESTA"
    ): List<Lugar> {

        val tipo = tipoPlan.uppercase()

        val radio = when (tipo) {
            "NOCHE", "FIESTA" -> 1500
            else -> 3000
        }

        val filtroAmenity = when (tipo) {
            "COMER" -> "restaurant|fast_food|cafe|bar"
            "NOCHE", "FIESTA" -> "bar|pub|nightclub|biergarten"
            "RELAX" -> "cafe|restaurant|park|garden"
            else -> "bar|pub|nightclub|restaurant|cafe"
        }

        val consulta = when (tipo) {
            "VISITAR" -> """
                [out:json][timeout:12];
                (
                  node(around:$radio,$lat,$lon)["tourism"~"^(museum|attraction|viewpoint|gallery|artwork)$"];
                  way(around:$radio,$lat,$lon)["tourism"~"^(museum|attraction|viewpoint|gallery|artwork)$"];
                  node(around:$radio,$lat,$lon)["historic"~"^(monument|castle|ruins|memorial|archaeological_site|wayside_cross)$"];
                  way(around:$radio,$lat,$lon)["historic"~"^(monument|castle|ruins|memorial|archaeological_site|wayside_cross)$"];
                );
                out center tags 20;
            """.trimIndent()

            else -> """
                [out:json][timeout:25];
                (
                  node(around:$radio,$lat,$lon)["amenity"~"^($filtroAmenity)$"];
                  way(around:$radio,$lat,$lon)["amenity"~"^($filtroAmenity)$"];
                );
                out center tags 30;
            """.trimIndent()
        }

        return try {
            Log.d("API_DEBUG", "Consulta Overpass: $consulta")

            val respuesta = apiOverpass.buscarPlanes(consulta)

            val lugares = respuesta.elements
                .mapNotNull { elemento ->
                    val tags = elemento.tags ?: return@mapNotNull null
                    val nombre = tags["name"] ?: return@mapNotNull null

                    val tipoLugar = tags["amenity"]
                        ?: tags["tourism"]
                        ?: tags["historic"]
                        ?: tipoPlan

                    val lugarLat = elemento.lat ?: elemento.center?.lat
                    val lugarLon = elemento.lon ?: elemento.center?.lon

                    if (lugarLat == null || lugarLon == null) return@mapNotNull null

                    Lugar(
                        nombre = nombre,
                        tipo = tipoLugar,
                        descripcion = crearDescripcion(tags),
                        lat = lugarLat,
                        lon = lugarLon,
                        puntuacion = generarPuntuacionEstimada(tipoLugar),
                        fotoUrl = null,
                        distanciaKm = calcularDistanciaKm(
                            lat.toDouble(),
                            lon.toDouble(),
                            lugarLat,
                            lugarLon
                        )
                    )
                }
                .distinctBy { it.nombre.lowercase() }
                .sortedBy { it.nombre }

            Log.d("API_DEBUG", "Encontrados ${lugares.size} sitios")
            Log.d("API_DEBUG", "Sitios encontrados: ${lugares.map { it.nombre }}")

            lugares

        } catch (e: Exception) {
            Log.e("API_DEBUG", "Error en Overpass: ${e.message}", e)
            emptyList()
        }
    }

    fun getSugerencias(query: String): Flow<List<String>> = flow {
        if (query.length < 2) {
            emit(emptyList())
            return@flow
        }

        try {
            val municipios = municipiosCache ?: apiService.getMunicipios()
                .map { it.label ?: "" }
                .distinct()
                .sorted()
                .also {
                    municipiosCache = it
                    Log.d("API_DEBUG", "Municipios cargados UNA vez: ${it.size}")
                }

            val resultados = municipios.filter {
                it.contains(query, ignoreCase = true)
            }

            emit(resultados)

        } catch (e: Exception) {
            Log.e("API_DEBUG", "Error real", e)
            emit(emptyList())
        }
    }

    private fun crearDescripcion(tags: Map<String, String>): String {
        val direccion = listOfNotNull(
            tags["addr:street"],
            tags["addr:housenumber"],
            tags["addr:city"]
        ).joinToString(" ")

        val cocina = tags["cuisine"]
        val telefono = tags["phone"] ?: tags["contact:phone"]
        val web = tags["website"] ?: tags["contact:website"]
        val horario = tags["opening_hours"]

        return listOfNotNull(
            direccion.takeIf { it.isNotBlank() },
            cocina?.let { "Cocina: $it" },
            telefono?.let { "Tel: $it" },
            web?.let { "Web: $it" },
            horario?.let { "Horario: $it" }
        ).joinToString("\n").ifBlank {
            "Información básica disponible en OpenStreetMap"
        }
    }
    private fun generarPuntuacionEstimada(tipo: String): Double {
        return when (tipo.lowercase()) {
            "restaurant" -> (4.4..4.8).randomRounded()
            "fast_food" -> (4.0..4.4).randomRounded()
            "cafe" -> (4.2..4.7).randomRounded()
            "bar" -> (4.1..4.6).randomRounded()
            "pub" -> (4.2..4.7).randomRounded()
            "nightclub" -> (4.0..4.5).randomRounded()
            "museum" -> (4.5..4.9).randomRounded()
            "attraction" -> (4.4..4.8).randomRounded()
            "viewpoint" -> (4.5..4.9).randomRounded()
            "monument" -> (4.4..4.8).randomRounded()
            "castle" -> (4.6..4.9).randomRounded()
            "park" -> (4.3..4.8).randomRounded()
            "garden" -> (4.3..4.8).randomRounded()
            else -> (4.0..4.6).randomRounded()
        }
    }

    private fun ClosedFloatingPointRange<Double>.randomRounded(): Double {
        return String.format(
            "%.1f",
            start + Math.random() * (endInclusive - start)
        ).replace(",", ".").toDouble()
    }
}