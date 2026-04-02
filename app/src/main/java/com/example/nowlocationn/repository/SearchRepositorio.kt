package com.example.nowlocationn.repository

import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class SearchRepositorio @Inject constructor() {

    //Lista (harcodeada) para el prototipo
    private val ciudades = listOf("Málaga", "Madrid", "Granada", "Barcelona", "Mallorca")
    //Funcion que filtra segun lo que el usuario escribe
    fun getSugerencias(query:String) : List<String> {
        return if(query.length >= 2){
            return ciudades.filter { it.contains(query, ignoreCase = true) }
        }else{
            emptyList()
        }
    }


}