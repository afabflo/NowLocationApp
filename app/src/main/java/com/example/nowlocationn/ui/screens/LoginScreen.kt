package com.example.nowlocationn.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.nowlocationn.viewmodel.AuthState
import com.example.nowlocationn.viewmodel.AuthViewModel

@Composable
fun LoginScreen(
    onLoginExitoso: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel()
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var esRegistro by remember { mutableStateOf(false) }
    var errorEmail by remember { mutableStateOf("") }
    var errorPassword by remember { mutableStateOf("") }

    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState) {
        if (uiState is AuthState.Success) {
            onLoginExitoso()
            viewModel.resetState()
        }
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
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "NowLocation",
                color = Color(0xFFE91E63),
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = if (esRegistro) "Crea tu cuenta" else "Inicia sesión",
                color = Color.White,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Campo Email
            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                    errorEmail = ""
                },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Email", color = Color(0xFF8F8FA3)) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = null,
                        tint = Color(0xFFE91E63)
                    )
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFF1B1B27),
                    unfocusedContainerColor = Color(0xFF1B1B27),
                    focusedBorderColor = Color(0xFFE91E63),
                    unfocusedBorderColor = Color(0xFF343445),
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                ),
                isError = errorEmail.isNotEmpty(),
                supportingText = {
                    if (errorEmail.isNotEmpty()) {
                        Text(text = errorEmail, color = Color.Red)
                    }
                }
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Campo Password
            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                    errorPassword = ""
                },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Contraseña", color = Color(0xFF8F8FA3)) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = null,
                        tint = Color(0xFFE91E63)
                    )
                },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFF1B1B27),
                    unfocusedContainerColor = Color(0xFF1B1B27),
                    focusedBorderColor = Color(0xFFE91E63),
                    unfocusedBorderColor = Color(0xFF343445),
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                ),
                isError = errorPassword.isNotEmpty(),
                supportingText = {
                    if (errorPassword.isNotEmpty()) {
                        Text(text = errorPassword, color = Color.Red)
                    }
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Error de Firebase
            if (uiState is AuthState.Error) {
                Text(
                    text = (uiState as AuthState.Error).mensaje,
                    color = Color.Red,
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botón principal
            Button(
                onClick = {
                    var valido = true

                    if (email.isBlank() || !email.contains("@")) {
                        errorEmail = "Introduce un email válido"
                        valido = false
                    }

                    if (password.length < 6) {
                        errorPassword = "La contraseña debe tener al menos 6 caracteres"
                        valido = false
                    }

                    if (valido) {
                        if (esRegistro) {
                            viewModel.registro(email, password)
                        } else {
                            viewModel.login(email, password)
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFE91E63)
                ),
                enabled = uiState !is AuthState.Loading
            ) {
                if (uiState is AuthState.Loading) {
                    CircularProgressIndicator(
                        color = Color.White,
                        modifier = Modifier.size(24.dp),
                        strokeWidth = 2.dp
                    )
                } else {
                    Text(
                        text = if (esRegistro) "Crear cuenta" else "Iniciar sesión",
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Cambiar entre login y registro
            TextButton(onClick = {
                esRegistro = !esRegistro
                viewModel.resetState()
                errorEmail = ""
                errorPassword = ""
            }) {
                Text(
                    text = if (esRegistro) "¿Ya tienes cuenta? Inicia sesión"
                    else "¿No tienes cuenta? Regístrate",
                    color = Color(0xFFE91E63)
                )
            }
        }
    }
}