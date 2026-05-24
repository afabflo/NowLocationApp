package com.example.nowlocationn.model

import com.google.gson.annotations.SerializedName

data class TicketmasterResponse(
    @SerializedName("_embedded") val embedded: EmbeddedEvents?
)

data class EmbeddedEvents(
    @SerializedName("events") val events: List<EventoDto>
)

data class EventoDto(
    @SerializedName("name") val nombre: String,
    @SerializedName("dates") val fechas: EventoFechas?,
    @SerializedName("priceRanges") val precios: List<EventoPrecio>?,
    @SerializedName("images") val imagenes: List<EventoImagen>?,
    @SerializedName("_embedded") val embedded: EventoEmbedded?,
    @SerializedName("url") val url: String?,
    @SerializedName("classifications") val clasificaciones: List<EventoClasificacion>?

)

data class EventoClasificacion(
    @SerializedName("segment") val segmento: EventoSegmento?
)

data class EventoSegmento(
    @SerializedName("name") val nombre: String?
)
data class EventoFechas(
    @SerializedName("start") val inicio: EventoInicio?
)

data class EventoInicio(
    @SerializedName("localDate") val fecha: String?,
    @SerializedName("localTime") val hora: String?
)

data class EventoPrecio(
    @SerializedName("min") val min: Double?,
    @SerializedName("currency") val moneda: String?
)

data class EventoImagen(
    @SerializedName("url") val url: String,
    @SerializedName("width") val width: Int?
)

data class EventoEmbedded(
    @SerializedName("venues") val venues: List<EventoVenue>?
)

data class EventoVenue(
    @SerializedName("name") val nombre: String?,
    @SerializedName("city") val ciudad: EventoCiudad?
)

data class EventoCiudad(
    @SerializedName("name") val nombre: String?
)