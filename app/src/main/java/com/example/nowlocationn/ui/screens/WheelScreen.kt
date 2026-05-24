package com.example.nowlocationn.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.nowlocationn.R

data class CategoriaUi(
    val nombre: String,
    val subtitulo: String,
    val emoji: Int,
    val colores: List<Color>
)

@Composable
fun WheelScreen(
    ciudad: String,
    onCategoriaClick: (String) -> Unit,
    onEventosClick:() -> Unit
) {
    val categorias = listOf(
        CategoriaUi(
            nombre = "Comer",
            subtitulo = "Restaurantes, cafés y sitios para probar algo bueno",
            emoji = R.drawable.cutlery,
            colores = listOf(
                Color(0xFF2A0A3D),
                Color(0xFF6A1B9A),
                Color(0xFFD1C4E9)
            )
        ),
        CategoriaUi(
            nombre = "Noche",
            subtitulo = "Bares, pubs y ambiente nocturno",
            emoji = R.drawable.neon,
            colores = listOf(
                Color(0xFF120018),
                Color(0xFF4A148C),
                Color(0xFFE1BEE7)
            )
        ),
        CategoriaUi(
            nombre = "Visitar",
            subtitulo = "Cultura, monumentos y lugares interesantes",
            emoji = R.drawable.pointer,
            colores = listOf(
                Color(0xFF1B1B2F),
                Color(0xFF512DA8),
                Color(0xFFDCC6E0)
            )
        ),
        CategoriaUi(
            nombre = "Relax",
            subtitulo = "Sitios tranquilos para desconectar",
            emoji = R.drawable.meditation,
            colores = listOf(
                Color(0xFF241332),
                Color(0xFF7B1FA2),
                Color(0xFFEDE7F6)
            )
        ),




    )

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFF101014)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color(0xFF1A1A24),
                            Color(0xFF101014)
                        )
                    )
                )
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(48.dp))

            Text(
                text = "NowLocation",
                color = Color(0xFFE91E63),
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Planes en $ciudad",
                color = Color.White,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Elige qué tipo de plan quieres descubrir hoy.",
                color = Color(0xFFB0B0B8),
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { onEventosClick() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFE91E63)
                )
            ) {
                Text(
                    text = " Eventos esta semana",
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(bottom = 90.dp)
            ) {
                items(categorias) { categoria ->
                    CategoriaCard(
                        categoria = categoria,
                        onClick = { onCategoriaClick(categoria.nombre) }
                    )
                }
            }
        }
    }
}

@Composable
fun CategoriaCard(
    categoria: CategoriaUi,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(145.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.linearGradient(categoria.colores)
                )
                .padding(20.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Image(
                    painter = painterResource(id = categoria.emoji),
                    contentDescription = categoria.nombre,
                    modifier = Modifier
                        .size(42.dp)
                        .alpha(0.95f)
                )
                Column {
                    Text(
                        text = categoria.nombre,
                        color = Color.White,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = categoria.subtitulo,
                        color = Color.White.copy(alpha = 0.85f),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}