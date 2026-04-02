package com.example.nowlocationn.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nowlocationn.repository.SearchRepositorio
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(application: Application,private val repositorio: SearchRepositorio) :
    AndroidViewModel(application) {
    private val _lugar = MutableStateFlow("")
    val lugar : StateFlow<String> = _lugar.asStateFlow()
    private val _sugerencias = MutableStateFlow<List<String>>(emptyList())
    val sugerencias : StateFlow<List<String>> = _sugerencias.asStateFlow()

    fun onLugarChange(nuevoLugar:String,onError:(String) -> Unit){
        viewModelScope.launch {
            if (nuevoLugar.isBlank()) {
                onError("Campo Obligatorio")
                return@launch
            }

        }
        _lugar.value = nuevoLugar //Actualizamos el texto escrito
        _sugerencias.value = repositorio.getSugerencias(nuevoLugar)  //Pedimos al repo la nueva sugerencia


    }
}