package com.example.nowlocationn.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.nowlocationn.ui.screens.DetallesScreen
import com.example.nowlocationn.ui.screens.SearchScreen
import com.example.nowlocationn.ui.screens.WheelScreen
import com.example.nowlocationn.viewmodel.SearchViewModel


object  Routes{
    const val  Search = "search"
    const val Wheel = "wheel/{ciudad}"
    const val Listado = "listado/{ciudad}/{categoria}"
}


@Composable
fun  NavHostScreen(navHostController: NavHostController){
    NavHost(startDestination = Routes.Search, navController =navHostController  ){
        composable(Routes.Search){
            val viewModel : SearchViewModel = hiltViewModel()

            SearchScreen(Modifier,viewModel, onCiudadClick = {ciudad -> navHostController.navigate(
                "wheel/$ciudad")})
        }
        composable(Routes.Wheel){
          /*Nombre del parametro*/  backStackEntry ->
            val ciudadSeleccionada = backStackEntry.arguments?.getString("ciudad") ?: ""
            WheelScreen(ciudadSeleccionada,onCategoriaClick={cat -> navHostController.navigate("listado/$ciudadSeleccionada/$cat")})
        }
        composable(Routes.Listado) {
            backStackentry -> val ciudad = backStackentry.arguments?.getString("ciudad") ?: ""
            val categoria = backStackentry.arguments?.getString("categoria") ?: ""
            DetallesScreen(ciudad,categoria)
        }


    }
}