package com.example.nowlocationn.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.nowlocationn.model.Lugar
import com.example.nowlocationn.ui.screens.DetallesScreen
import com.example.nowlocationn.ui.screens.EventosScreen
import com.example.nowlocationn.ui.screens.FavoritosScreen
import com.example.nowlocationn.ui.screens.LoginScreen
import com.example.nowlocationn.ui.screens.LugarDetalleScreen
import com.example.nowlocationn.ui.screens.OnboardingScreen
import com.example.nowlocationn.ui.screens.SearchScreen
import com.example.nowlocationn.ui.screens.SplashScreen
import com.example.nowlocationn.ui.screens.WheelScreen
import com.example.nowlocationn.ui.screens.onboardingYaVisto
import com.example.nowlocationn.viewmodel.SearchViewModel
import com.google.firebase.auth.FirebaseAuth

object Routes {
    const val Splash = "splash"
    const val Onboarding = "onboarding"
    const val Login = "login"

    const val Search = "search"
    const val Eventos = "eventos/{ciudad}"

    const val Wheel = "wheel/{ciudad}"
    const val Listado = "listado/{ciudad}/{categoria}"
    const val LugarDetalle = "lugarDetalle"
    const val Favoritos = "favoritos"

}

@Composable
fun NavHostScreen(navHostController: NavHostController) {
    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()
    val startDestination = if (auth.currentUser != null) Routes.Search else Routes.Login

    NavHost(
        startDestination = startDestination,
        navController = navHostController
    ) {

        composable(Routes.Login) {
            LoginScreen(
                onLoginExitoso = {
                    navHostController.navigate(Routes.Splash) {
                        popUpTo(Routes.Login) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(Routes.Splash) {
            SplashScreen(
                onNavigateToSearch = {
                    val destino = if (onboardingYaVisto(context)) {
                        Routes.Search
                    } else {
                        Routes.Onboarding
                    }

                    navHostController.navigate(destino) {
                        popUpTo(Routes.Splash) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(Routes.Onboarding) {
            OnboardingScreen(
                onFinish = {
                    navHostController.navigate(Routes.Search) {
                        popUpTo(Routes.Onboarding) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(Routes.Search) {
            val viewModel: SearchViewModel = hiltViewModel()

            SearchScreen(
                modifier = Modifier,
                viewModel = viewModel,
                onCiudadClick = { ciudad ->
                    navHostController.navigate("wheel/$ciudad")
                },
                onFavoritosClick = {
                    navHostController.navigate(Routes.Favoritos)
                }
            )
        }

        composable(Routes.Wheel) { backStackEntry ->
            val ciudadSeleccionada =
                backStackEntry.arguments?.getString("ciudad") ?: ""

            WheelScreen(
                ciudad = ciudadSeleccionada,
                onCategoriaClick = { categoria ->
                    navHostController.navigate("listado/$ciudadSeleccionada/$categoria")
                },
                onEventosClick = {
                    navHostController.navigate("eventos/$ciudadSeleccionada")
                }
            )
        }


        composable(Routes.Listado) { backStackEntry ->
            val ciudad = backStackEntry.arguments?.getString("ciudad") ?: ""
            val categoria = backStackEntry.arguments?.getString("categoria") ?: ""

            DetallesScreen(
                ciudad = ciudad,
                categoria = categoria,
                onLugarClick = { lugar ->
                    navHostController.currentBackStackEntry
                        ?.savedStateHandle
                        ?.set("lugarSeleccionado", lugar)

                    navHostController.navigate(Routes.LugarDetalle)
                }
            )
        }

        composable(Routes.LugarDetalle) {
            val lugar = navHostController
                .previousBackStackEntry
                ?.savedStateHandle
                ?.get<Lugar>("lugarSeleccionado")

            if (lugar != null) {
                LugarDetalleScreen(
                    nombre = lugar.nombre,
                    tipo = lugar.tipo,
                    descripcion = lugar.descripcion,
                    lat = lugar.lat,
                    lon = lugar.lon,
                    puntuacion = lugar.puntuacion
                )
            }
        }

        composable(Routes.Eventos) { backStackEntry ->
            val ciudad = backStackEntry.arguments?.getString("ciudad") ?: ""
            EventosScreen(ciudad = ciudad)
        }

        composable(Routes.Favoritos) {
            FavoritosScreen(
                onLugarClick = { lugar ->
                    navHostController.currentBackStackEntry
                        ?.savedStateHandle
                        ?.set("lugarSeleccionado", lugar)

                    navHostController.navigate(Routes.LugarDetalle)
                }
            )
        }
    }
}