package com.example.nowlocationn.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ListItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.nowlocationn.viewmodel.SearchViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SearchScreen(modifier: Modifier,viewModel: SearchViewModel,onCiudadClick:(String) -> Unit ) {
    var lugar by remember { mutableStateOf("") }
    val ciudades = listOf("Málaga", "Madrid", "Granada", "Barcelona", "Mallorca")
    val sugerencias = if(lugar.length>=2){
        ciudades.filter { it.contains(lugar, ignoreCase = true) }
    }else{
        emptyList()
    }

        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally){
            Text("¿Donde estas?")
            Spacer(modifier = Modifier.padding(8.dp))
            OutlinedTextField(
                value = lugar,
                onValueChange = {lugar = it}
                ,modifier=Modifier.fillMaxWidth()
            )
            LazyColumn {
                items(sugerencias) {
                    ciudad ->
                    ListItem(
                        headlineContent = {Text(ciudad)},
                        modifier = Modifier.clickable{
                           onCiudadClick(ciudad)
                        }
                    )
                }
            }
        }

}