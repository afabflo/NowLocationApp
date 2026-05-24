package com.example.nowlocationn.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor() : ViewModel() {

    private val auth = FirebaseAuth.getInstance()

    private val _uiState = MutableStateFlow<AuthState>(AuthState.Idle)
    val uiState: StateFlow<AuthState> = _uiState.asStateFlow()

    val usuarioActual get() = auth.currentUser

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = AuthState.Loading
            try {
                auth.signInWithEmailAndPassword(email, password).await()
                _uiState.value = AuthState.Success
            } catch (e: Exception) {
                _uiState.value = AuthState.Error(e.message ?: "Error al iniciar sesión")
            }
        }
    }

    fun registro(email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = AuthState.Loading
            try {
                auth.createUserWithEmailAndPassword(email, password).await()
                _uiState.value = AuthState.Success
            } catch (e: Exception) {
                _uiState.value = AuthState.Error(e.message ?: "Error al registrarse")
            }
        }
    }

    fun logout() {
        auth.signOut()
        _uiState.value = AuthState.Idle
    }

    fun resetState() {
        _uiState.value = AuthState.Idle
    }
}

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    object Success : AuthState()
    data class Error(val mensaje: String) : AuthState()
}