package com.example.nowlocationn.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.nowlocationn.model.EventoDto
import com.example.nowlocationn.viewmodel.EventosViewModel
@Composable
fun EventosScreen(
    ciudad: String,
    viewModel: EventosViewModel = hiltViewModel()
) {
    val eventos by viewModel.eventos.collectAsState()
    val cargando by viewModel.cargando.collectAsState()

    var filtroSeleccionado by remember { mutableStateOf("Todo") }


    val categorias = listOf("Todo", "Música", "Deportes", "Arte", "Familia")

    val eventosFiltrados = remember(eventos, filtroSeleccionado) {
        if (filtroSeleccionado == "Todo") {
            eventos
        } else {
            val termino = when (filtroSeleccionado) {
                "Música" -> "Music"
                "Deportes" -> "Sports"
                "Arte" -> "Arts"
                "Familia" -> "Family"
                else -> filtroSeleccionado
            }
            eventos.filter { evento ->
                evento.clasificaciones
                    ?.firstOrNull()
                    ?.segmento?.nombre
                    ?.contains(termino, ignoreCase = true) == true
            }
        }
    }


    LaunchedEffect(ciudad) {
        viewModel.buscarEventos(ciudad)
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFF0D0D12)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(Color(0xFF191923), Color(0xFF0D0D12))
                    )
                )
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(52.dp))

            Text(
                text = "NowLocation",
                color = Color(0xFFE91E63),
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Eventos en $ciudad",
                color = Color.White,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = when {
                    cargando -> "Buscando eventos..."
                    eventosFiltrados.isEmpty() -> "No se encontraron eventos."
                    else -> "${eventosFiltrados.size} eventos encontrados"
                },
                color = Color(0xFFB8B8C7),
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Chips de filtro
            androidx.compose.foundation.lazy.LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(vertical = 4.dp)
            ) {
                items(categorias) { categoria ->
                    val seleccionado = categoria == filtroSeleccionado
                    Box(
                        modifier = Modifier
                            .background(
                                color = if (seleccionado) Color(0xFFE91E63)
                                else Color(0xFF1A1A26),
                                shape = RoundedCornerShape(50)
                            )
                            .clickable { filtroSeleccionado = categoria }
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = categoria,
                            color = Color.White,
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = if (seleccionado) FontWeight.Bold
                            else FontWeight.Normal
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            when {
                cargando -> LoadingCard()
                eventosFiltrados.isEmpty() -> {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(22.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFF1A1A26)
                        )
                    ) {
                        Column(modifier = Modifier.padding(20.dp)) {
                            Text(
                                text = "Sin eventos en esta categoría",
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Prueba con otra categoría o consulta más adelante.",
                                color = Color(0xFFB8B8C7)
                            )
                        }
                    }
                }
                else -> {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(14.dp),
                        contentPadding = PaddingValues(bottom = 100.dp)
                    ) {
                        items(eventosFiltrados) { evento ->
                            EventoCard(evento = evento)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun EventoCard(evento: EventoDto) {
    val imagenUrl = evento.imagenes
        ?.filter { it.width != null }
        ?.maxByOrNull { it.width!! }
        ?.url

    val precio = evento.precios?.firstOrNull()
    val venue = evento.embedded?.venues?.firstOrNull()
    val fecha = evento.fechas?.inicio?.fecha?.let {
        val partes = it.split("-")
        if (partes.size == 3) "${partes[2]}/${partes[1]}/${partes[0]}" else it
    } ?: "Fecha por confirmar"
    val hora = evento.fechas?.inicio?.hora?.take(5) ?: ""

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1A1A26)
        )
    ) {
        Column {
            if (imagenUrl != null) {
                AsyncImage(
                    model = imagenUrl,
                    contentDescription = evento.nombre,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                        .clip(RoundedCornerShape(topStart = 22.dp, topEnd = 22.dp))
                )
            }

            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = evento.nombre,
                    color = Color.White,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "$fecha ${if (hora.isNotBlank()) "· $hora" else ""}",
                        color = Color(0xFFB8B8C7),
                        style = MaterialTheme.typography.bodySmall
                    )

                    if (precio?.min != null) {
                        Text(
                            text = "Desde ${precio.min}€",
                            color = Color(0xFFE91E63),
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                venue?.nombre?.let {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = " $it",
                        color = Color(0xFFB8B8C7),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
                evento.url?.let { url ->
                    Spacer(modifier = Modifier.height(10.dp))

                    val context = LocalContext.current

                    Button(
                        onClick = {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                            context.startActivity(intent)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFE91E63)
                        )
                    ) {
                        Text(
                            text = " Ver entradas",
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}