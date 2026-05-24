package com.example.nowlocationn.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.nowlocationn.R
import com.example.nowlocationn.model.Lugar
import com.example.nowlocationn.viewmodel.FavoritosViewModel


@Composable
fun LugarDetalleScreen(
    nombre: String,
    tipo: String,
    descripcion: String,
    lat: Double?,
    lon: Double?,
    puntuacion: Double = 0.0,
    favoritosViewModel: FavoritosViewModel = hiltViewModel()
){
    val context = LocalContext.current

    val esFavorito by favoritosViewModel.esFavorito.collectAsState()

    val lugarActual = Lugar(
        nombre = nombre,
        tipo = tipo,
        descripcion = descripcion,
        lat = lat,
        lon = lon,
        puntuacion = puntuacion
    )

    LaunchedEffect(nombre) {
        favoritosViewModel.comprobarFavorito(nombre)
    }

    val imagenRes = remember(nombre, tipo) {
        obtenerImagenAleatoria(tipo)
    }

    val descripcionMejorada = generarDescripcionBonita(
        nombre = nombre,
        tipo = tipo,
        descripcionBase = descripcion
    )

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
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(48.dp))

            Image(
                painter = painterResource(id = imagenRes),
                contentDescription = nombre,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
                    .clip(RoundedCornerShape(28.dp))
            )

            Spacer(modifier = Modifier.height(22.dp))

            Text(
                text = nombre,
                color = Color.White,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(14.dp))

            OutlinedButton(
                onClick = {
                    favoritosViewModel.cambiarFavorito(lugarActual)
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Color(0xFFE91E63)
                )
            ) {
                Icon(
                    imageVector = if (esFavorito) {
                        Icons.Default.Favorite
                    } else {
                        Icons.Default.FavoriteBorder
                    },
                    contentDescription = null
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = if (esFavorito) {
                        "Guardado en favoritos"
                    } else {
                        "Guardar en favoritos"
                    },
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                TipoBadge(tipo = tipo)

                Spacer(modifier = Modifier.width(10.dp))

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
                        text = if (lugarActual.puntuacion > 0.0) {
                            "${lugarActual.puntuacion} · Muy recomendado"
                        } else {
                            "Lugar guardado · Recomendado"
                        },
                        color = Color(0xFFB8B8C7),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            InfoCard(
                titulo = "Descripción",
                contenido = descripcionMejorada
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (lat != null && lon != null) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(22.dp),
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
                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = null,
                                tint = Color(0xFFE91E63)
                            )

                            Spacer(modifier = Modifier.width(8.dp))

                            Text(
                                text = "Ubicación",
                                color = Color.White,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = "$lat, $lon",
                            color = Color(0xFFB8B8C7),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = {
                        val uri = Uri.parse("geo:$lat,$lon?q=$lat,$lon($nombre)")
                        val intent = Intent(Intent.ACTION_VIEW, uri)
                        context.startActivity(intent)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    shape = RoundedCornerShape(18.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFE91E63)
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = null
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = "Abrir en Google Maps",
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedButton(
                    onClick = {
                        val texto = """
                            Te propongo este plan en NowLocation:
                  
                            $nombre
                            Tipo: ${formatearTipoLugar(tipo)}
                            Ubicación: $lat, $lon
                        
                            ¿Vamos?
                        """.trimIndent()

                        val intent = Intent(Intent.ACTION_SEND).apply {
                            type = "text/plain"
                            putExtra(Intent.EXTRA_TEXT, texto)
                        }

                        context.startActivity(
                            Intent.createChooser(intent, "Compartir lugar")
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    shape = RoundedCornerShape(18.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color(0xFFE91E63)
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = null
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = "Compartir lugar",
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}
@Composable
fun TipoBadge(tipo: String) {
    Box(
        modifier = Modifier
            .background(
                brush = Brush.linearGradient(
                    listOf(
                        Color(0xFFE91E63),
                        Color(0xFF6A1B9A)
                    )
                ),
                shape = RoundedCornerShape(50)
            )
            .padding(horizontal = 14.dp, vertical = 7.dp)
    ) {
        Text(
            text = formatearTipoLugar(tipo),
            color = Color.White,
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun InfoCard(
    titulo: String,
    contenido: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1A1A26)
        )
    ) {
        Column(
            modifier = Modifier.padding(18.dp)
        ) {
            Text(
                text = titulo,
                color = Color.White,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = contenido,
                color = Color(0xFFB8B8C7),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

fun obtenerImagenAleatoria(tipo: String): Int {
    val lista = when (tipo.lowercase()) {
        "restaurant", "fast_food", "cafe" -> listOf(
            R.drawable.food_1
        )

        "bar", "pub", "nightclub" -> listOf(
            R.drawable.night_1
        )

        "museum", "attraction", "monument", "castle", "viewpoint" -> listOf(
            R.drawable.visit_1
        )

        else -> listOf(
            R.drawable.default_1
        )
    }

    return lista.random()
}

fun generarDescripcionBonita(
    nombre: String,
    tipo: String,
    descripcionBase: String
): String {
    val intro = when (tipo.lowercase()) {
        "restaurant" ->
            "$nombre es una excelente opción gastronómica para quienes buscan una experiencia culinaria agradable."

        "fast_food" ->
            "$nombre destaca como una parada rápida y cómoda para disfrutar de comida accesible."

        "cafe" ->
            "$nombre ofrece un ambiente ideal para relajarse, tomar algo y disfrutar de una pausa."

        "bar" ->
            "$nombre es un punto interesante para disfrutar del ocio local y un ambiente social."

        "pub" ->
            "$nombre combina entretenimiento y ambiente nocturno en una propuesta atractiva."

        "nightclub" ->
            "$nombre representa una opción pensada para quienes buscan vida nocturna y energía."

        "museum" ->
            "$nombre aporta una experiencia cultural enriquecedora."

        "attraction" ->
            "$nombre puede convertirse en una visita interesante dentro de la zona."

        "viewpoint" ->
            "$nombre puede ser una parada interesante para descubrir la zona desde otra perspectiva."

        "monument" ->
            "$nombre representa un punto histórico o cultural destacado."

        "castle" ->
            "$nombre ofrece una experiencia visual e histórica con gran atractivo."

        else ->
            "$nombre es un lugar destacado dentro de su categoría."
    }

    return if (
        descripcionBase.isNotBlank() &&
        descripcionBase != "Sin descripción disponible"
    ) {
        "$intro\n\nInformación adicional:\n$descripcionBase"
    } else {
        "$intro\n\nActualmente hay poca información detallada disponible, pero puede ser una opción interesante para explorar."
    }
}