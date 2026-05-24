package com.example.nowlocationn.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onNavigateToSearch: () -> Unit
) {
    LaunchedEffect(Unit) {
        delay(2200)
        onNavigateToSearch()
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFF0D0D12)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color(0xFF0D0D12),
                            Color(0xFF191923),
                            Color(0xFF6A1B9A)
                        )
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "NowLocation",
                    color = Color.White,
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.ExtraBold
                )

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = "Tu guía inteligente para descubrir ciudades",
                    color = Color(0xFFE1BEE7),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}