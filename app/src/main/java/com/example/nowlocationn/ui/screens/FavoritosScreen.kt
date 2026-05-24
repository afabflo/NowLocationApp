package com.example.nowlocationn.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.nowlocationn.data.local.FavoritoEntity
import com.example.nowlocationn.model.Lugar
import com.example.nowlocationn.viewmodel.FavoritosViewModel

@Composable
fun FavoritosScreen(
    onLugarClick: (Lugar) -> Unit,
    viewModel: FavoritosViewModel = hiltViewModel()
) {
    val favoritos by viewModel.favoritos.collectAsState(initial = emptyList())

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
                text = "NowLocation",
                color = Color(0xFFE91E63),
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Mis favoritos",
                color = Color.White,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "${favoritos.size} lugares guardados",
                color = Color(0xFFB8B8C7),
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(24.dp))

            if (favoritos.isEmpty()) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(22.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF1A1A26)
                    )
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Text(
                            text = "Todavía no tienes favoritos",
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Guarda lugares desde la pantalla de detalle para verlos aquí.",
                            color = Color(0xFFB8B8C7)
                        )
                    }
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(14.dp),
                    contentPadding = PaddingValues(bottom = 100.dp)
                ) {
                    items(favoritos) { favorito ->
                        FavoritoCard(
                            favorito = favorito,
                            onClick = {
                                onLugarClick(
                                    Lugar(
                                        nombre = favorito.nombre,
                                        tipo = favorito.tipo,
                                        descripcion = favorito.descripcion,
                                        lat = favorito.lat,
                                        lon = favorito.lon
                                    )
                                )
                            },
                            onEliminar = {
                                viewModel.eliminarFavorito(favorito)
                            }
                        )
                    }
                }
            }
        }
    }
}
@Composable
fun FavoritoCard(
    favorito: FavoritoEntity,
    onClick: () -> Unit,
    onEliminar: () -> Unit
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
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = null,
                tint = Color(0xFFE91E63)
            )

            Spacer(modifier = Modifier.width(14.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = favorito.nombre,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    text = formatearTipoLugar(favorito.tipo),
                    color = Color(0xFFE1BEE7),
                    style = MaterialTheme.typography.bodySmall
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Ver detalle →",
                    color = Color(0xFFE91E63),
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            IconButton(
                onClick = onEliminar
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Eliminar favorito",
                    tint = Color(0xFFE91E63)
                )
            }
        }
    }
}