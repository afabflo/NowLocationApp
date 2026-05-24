package com.example.nowlocationn.ui.screens

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext

data class OnboardingPage(
    val titulo: String,
    val descripcion: String
)

@Composable
fun OnboardingScreen(
    onFinish: () -> Unit
) {
    val context = LocalContext.current

    val pages = listOf(
        OnboardingPage(
            "Descubre ciudades fácilmente",
            "Encuentra lugares relevantes sin saturación innecesaria."
        ),
        OnboardingPage(
            "Elige por intención",
            "Comer, Noche, Visitar o Relax según lo que realmente necesitas."
        ),
        OnboardingPage(
            "Guarda y adapta",
            "Crea tu experiencia personalizada con favoritos e historial."
        )
    )

    var currentPage by remember { mutableStateOf(0) }

    val page = pages[currentPage]

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
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = page.titulo,
                color = Color.White,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = page.descripcion,
                color = Color(0xFFB8B8C7),
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(42.dp))

            Button(
                onClick = {
                    if (currentPage < pages.lastIndex) {
                        currentPage++
                    } else {
                        guardarOnboardingVisto(context)
                        onFinish()
                    }
                },
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFE91E63)
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = if (currentPage < pages.lastIndex) {
                        "Siguiente"
                    } else {
                        "Empezar"
                    },
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

fun guardarOnboardingVisto(context: Context) {
    val prefs = context.getSharedPreferences("nowlocation_prefs", Context.MODE_PRIVATE)
    prefs.edit().putBoolean("onboarding_visto", true).apply()
}

fun onboardingYaVisto(context: Context): Boolean {
    val prefs = context.getSharedPreferences("nowlocation_prefs", Context.MODE_PRIVATE)
    return prefs.getBoolean("onboarding_visto", false)
}