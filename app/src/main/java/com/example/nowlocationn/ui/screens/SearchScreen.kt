package com.example.nowlocationn.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.nowlocationn.viewmodel.SearchViewModel
import androidx.compose.material.icons.filled.Delete
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.nowlocationn.viewmodel.HistorialViewModel

@Composable
fun SearchScreen(
    modifier: Modifier,
    viewModel: SearchViewModel,
    onCiudadClick: (String) -> Unit,
    onFavoritosClick: () -> Unit,
    historialViewModel: HistorialViewModel = hiltViewModel()
) {
    var query by remember { mutableStateOf("") }

    val sugerencias by viewModel.sugerencias.collectAsState()
    val historial by historialViewModel.historial.collectAsState(initial = emptyList())


    Surface(
        modifier = modifier.fillMaxSize(),
        color = Color(0xFF0D0D12)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF191923),
                            Color(0xFF0D0D12),
                            Color(0xFF12121A)
                        )
                    )
                )
                .padding(horizontal = 20.dp)
        ) {

            Spacer(modifier = Modifier.height(60.dp))

            Text(
                text = "NowLocation",
                color = Color(0xFFE91E63),
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = "Descubre tu próximo destino",
                color = Color.White,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Encuentra lugares para comer, salir, visitar o relajarte en cualquier ciudad.",
                color = Color(0xFFB8B8C7),
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(30.dp))

            OutlinedTextField(
                value = query,
                onValueChange = {
                    query = it
                    viewModel.buscarMunicipios(it)
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(22.dp),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Buscar",
                        tint = Color(0xFFE91E63)
                    )
                },
                placeholder = {
                    Text(
                        text = "Busca ciudad... Madrid, Málaga, Granada...",
                        color = Color(0xFF8F8FA3)
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFF1B1B27),
                    unfocusedContainerColor = Color(0xFF1B1B27),
                    focusedBorderColor = Color(0xFFE91E63),
                    unfocusedBorderColor = Color(0xFF343445),
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    cursorColor = Color(0xFFE91E63)
                )
            )
            Spacer(modifier = Modifier.height(14.dp))

            Button(
                onClick = {
                    onFavoritosClick()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF6A1B9A)
                )
            ) {
                Text(
                    text = "Ver favoritos",
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(22.dp))

            when {
                query.isBlank() -> {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFF171721)
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(20.dp)
                        ) {
                            Text(
                                text = "Empieza tu aventura",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.titleMedium
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = "Busca una ciudad para descubrir experiencias personalizadas.",
                                color = Color(0xFFA8A8B8)
                            )
                        }
                    }

                    if (historial.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(22.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Búsquedas recientes",
                                color = Color.White,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )

                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Borrar historial",
                                tint = Color(0xFFE91E63),
                                modifier = Modifier.clickable {
                                    historialViewModel.borrarHistorial()
                                }
                            )
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                            contentPadding = PaddingValues(bottom = 100.dp)
                        ) {
                            items(historial) { item ->
                                CiudadRecienteCard(
                                    ciudad = item.ciudad,
                                    onClick = {
                                        historialViewModel.guardarCiudad(item.ciudad)
                                        onCiudadClick(item.ciudad)
                                    }
                                )
                            }
                        }
                    }
                }

                else -> {
                    Text(
                        text = "${sugerencias.size} ciudades encontradas",
                        color = Color(0xFFA8A8B8),
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(14.dp),
                        contentPadding = PaddingValues(bottom = 100.dp)
                    ) {
                        items(sugerencias.take(20)) { ciudad ->
                            CiudadPremiumCard(
                                ciudad = ciudad,
                                onClick = {
                                    query = ciudad
                                    historialViewModel.guardarCiudad(ciudad)
                                    onCiudadClick(ciudad)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CiudadPremiumCard(
    ciudad: String,
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
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = "Ciudad",
                    tint = Color(0xFFE91E63)
                )

                Spacer(modifier = Modifier.width(10.dp))

                Column {
                    Text(
                        text = ciudad,
                        color = Color.White,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "Descubrir planes y experiencias",
                        color = Color(0xFF9D9DB0),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            HorizontalDivider(
                color = Color(0xFF2D2D3C)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Explora gastronomía, vida nocturna, turismo y relax.",
                color = Color(0xFFC2C2D0),
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}
@Composable
fun CiudadRecienteCard(
    ciudad: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1A1A26)
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = null,
                tint = Color(0xFFE91E63)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text(
                    text = ciudad,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    text = "Continuar explorando esta ciudad",
                    color = Color(0xFF9D9DB0),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}