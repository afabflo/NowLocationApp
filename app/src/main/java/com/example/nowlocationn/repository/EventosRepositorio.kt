package com.example.nowlocationn.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.nowlocationn.interfaces.TicketmasterApiService
import com.example.nowlocationn.model.EventoDto
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EventosRepositorio @Inject constructor(
    private val api: TicketmasterApiService
) {
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun buscarEventos(ciudad: String): List<EventoDto> {
        return try {
            val hoy = LocalDate.now()
            val finSemana = hoy.plusDays(7)
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")

            val start = hoy.atStartOfDay().format(formatter)
            val end = finSemana.atStartOfDay().format(formatter)

            val response = api.buscarEventos(
                apiKey = "K06V2aH7ugo6kZdg2e2y1Uuv4iYrNGZk",
                ciudad = ciudad,
                startDateTime = start,
                endDateTime = end
            )
            response.embedded?.events ?: emptyList()
        } catch (e: Exception) {
            android.util.Log.e("EVENTOS_DEBUG", "Error: ${e.message}")
            emptyList()
        }
    }
}