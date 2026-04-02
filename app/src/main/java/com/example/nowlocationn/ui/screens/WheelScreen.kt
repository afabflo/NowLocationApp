package com.example.nowlocationn.ui.screens

import android.R
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.input.pointer.pointerInput

@Composable
fun WheelScreen(ciudad: String,onCategoriaClick: (String) -> Unit) {

    val categorias = listOf("Comer","Noche","Visitar","Relax",)
    val fondoGradient = Brush.radialGradient(
        colors = listOf(Color(0xFF1E1E2E), Color(0xFF121212)),
        radius = 1000f
    )
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFF121212)
    ) {
        Column{
            Text(text = "Planes en $ciudad",modifier = Modifier
                .padding(top = 40.dp, start = 20.dp, bottom = 10.dp),
                style = MaterialTheme.typography.displaySmall,
                color = Color.White,
                fontWeight = FontWeight.Bold)
            LazyColumn() {

                items(categorias) { categoria ->
                    CartaIndividual(categoria, onCategoriaClick = {selectedcat -> onCategoriaClick(selectedcat)})
                }

            }
        }
    }
}
@Composable
fun CartaIndividual(categoria :String,onCategoriaClick:(String) -> Unit){
    val gradientColors = listOf(
        Color(0xFF6200EE), // Morado profundo
        Color(0xFFE91E63), // Rosa vibrante / Fucsia
        Color(0xFF9C27B0)  // Violeta
    )
    Card(modifier =
        Modifier.fillMaxWidth().
        height(130.dp).
        padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp), colors = CardDefaults.cardColors(Color.Black))
    {
        Box(modifier = Modifier.fillMaxSize()){
            Column(modifier = Modifier.fillMaxSize().background(brush = Brush.linearGradient(colors = gradientColors)).padding(16.dp).clickable{(onCategoriaClick(categoria))}, verticalArrangement = Arrangement.Bottom)
            {
                Text(text = categoria,
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.White,
                    fontWeight = FontWeight.Bold)
                Text(text = "Explora los mejores sitios",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray)
            }
        }

    }

}