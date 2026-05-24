package com.example.nowlocationn.ui.screens

import android.graphics.drawable.Icon
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.nowlocationn.model.Lugar
import com.example.nowlocationn.viewmodel.DetallesViewModel

@Composable
fun DetallesScreen(
    ciudad: String,
    categoria: String,
    onLugarClick: (Lugar) -> Unit,
    viewModel: DetallesViewModel = hiltViewModel()
) {
    val lugares by viewModel.lugares.collectAsState()
    val cargando by viewModel.cargando.collectAsState()

    LaunchedEffect(ciudad, categoria) {
        android.util.Log.d("PLANES_DEBUG", "ENTRO EN DETALLES ciudad=$ciudad categoria=$categoria")
        viewModel.cargarPlanes(ciudad, categoria)
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
                        listOf(
                            Color(0xFF191923),
                            Color(0xFF0D0D12)
                        )
                    )
                )
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(52.dp))

            Text(
                text = categoria,
                color = Color(0xFFE91E63),
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "$categoria en $ciudad",
                color = Color.White,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = when {
                    cargando -> "Buscando lugares interesantes cerca de la zona..."
                    lugares.isEmpty() -> "Explora planes disponibles según la información abierta de la ciudad."
                    else -> "${lugares.size} lugares encontrados para ti."
                },
                color = Color(0xFFB8B8C7),
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(24.dp))

            when {
                cargando -> {
                    LoadingCard()
                }

                lugares.isEmpty() -> {
                    EmptyPlacesCard(categoria = categoria)
                }

                else -> {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(14.dp),
                        contentPadding = PaddingValues(bottom = 100.dp)
                    ) {
                        items(lugares) { lugar ->
                            TarjetaLugar(
                                lugar = lugar,
                                onClick = { onLugarClick(lugar) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TarjetaLugar(
    lugar: Lugar,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(22.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1A1A26)
        )
    ) {
        Column(
            modifier = Modifier.padding(18.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .background(
                            Brush.linearGradient(
                                listOf(
                                    Color(0xFFE91E63),
                                    Color(0xFF6A1B9A)
                                )
                            ),
                            shape = RoundedCornerShape(14.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Place,
                        contentDescription = null,
                        tint = Color.White
                    )
                }

                Spacer(modifier = Modifier.width(14.dp))

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = lugar.nombre,
                        color = Color.White,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    Text(
                        text = formatearTipoLugar(lugar.tipo),
                        color = Color(0xFFE1BEE7),
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Medium
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = Color(0xFFE91E63),
                        modifier = Modifier.size(18.dp)
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = lugar.puntuacion.toString(),
                        color = Color.White,
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = lugar.descripcion,
                color = Color(0xFFB8B8C7),
                style = MaterialTheme.typography.bodySmall,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )

            lugar.distanciaKm?.let { distancia ->
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = " Aprox. $distancia km del centro",
                    color = Color(0xFFB8B8C7),
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = "Ver detalles →",
                color = Color(0xFFE91E63),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun LoadingCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1A1A26)
        )
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CircularProgressIndicator(
                color = Color(0xFFE91E63),
                modifier = Modifier.size(28.dp),
                strokeWidth = 3.dp
            )

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = "Cargando lugares...",
                color = Color.White,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun EmptyPlacesCard(categoria: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1A1A26)
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = "No hay lugares disponibles",
                color = Color.White,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "No se encontraron sitios de $categoria en esta zona. Prueba con otra categoría o ciudad.",
                color = Color(0xFFB8B8C7),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

fun formatearTipoLugar(tipo: String): String {
    return when (tipo.lowercase()) {
        "restaurant" -> "Restaurante"
        "fast_food" -> "Comida rápida"
        "cafe" -> "Café"
        "bar" -> "Bar"
        "pub" -> "Pub"
        "nightclub" -> "Discoteca"
        "museum" -> "Museo"
        "attraction" -> "Atracción"
        "viewpoint" -> "Mirador"
        "monument" -> "Monumento"
        "castle" -> "Castillo"
        "park" -> "Parque"
        "garden" -> "Jardín"
        else -> tipo.replaceFirstChar { it.uppercase() }
    }
}