package com.example.nowlocationn.ui.screens

import android.view.Surface
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.nowlocationn.model.Lugar
@Composable
fun DetallesScreen(ciudad:String,categoria:String) {
    val todosLosLugares = listOf(
        Lugar("Restaurante El Pimpi", "Bodega emblemática de Málaga.", "Comer", "Málaga", 4.8),
        Lugar("Discoteca Mae West", "La mejor fiesta de Granada.", "Noche", "Granada", 4.5),
        Lugar("La Alcazaba", "Fortificación palaciega de época islámica.", "Visitar", "Málaga", 4.9),
    )
    val lugaresFiltrados = todosLosLugares.filter { it.ciudad.contains(ciudad, ignoreCase = true) && it.categoria.lowercase() == categoria.lowercase() }

    Surface(modifier = Modifier.fillMaxSize(), color = Color(0xFF121212)){
        Column(modifier = Modifier.padding(16.dp)){
            Text(text = "$categoria en $ciudad"
            , style = MaterialTheme.typography.displaySmall
            , color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 20.dp))
            if(lugaresFiltrados.isEmpty()){
                Text("No hay lugares registrados aún.", color = Color.Gray)
            }else{
               LazyColumn {
                   items(lugaresFiltrados){ lugar -> TarjetaLugar(lugar)}
               }
            }
        }
    }
}

@Composable
fun TarjetaLugar(lugar: Lugar){
    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E2E))) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = lugar.nombre, color = Color.White, fontWeight = FontWeight.Bold)
                Text(text = lugar.descripcion, color = Color.Gray, style = MaterialTheme.typography.bodySmall)
            }

            Text(
                text = "⭐ ${lugar.puntuacion}",
                color = Color(0xFFE91E63),
                fontWeight = FontWeight.Bold
            )
        }
    }
}