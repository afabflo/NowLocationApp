# NowLocation 

**NowLocation** es una aplicación de Android moderna construida con Jetpack Compose que ayuda a los usuarios a descubrir los mejores lugares (restaurantes, ocio nocturno, visitas culturales y relax) en diferentes ciudades de España.

El proyecto utiliza una arquitectura limpia y las últimas bibliotecas recomendadas por Google para el desarrollo de Android.

##  Características

- **Búsqueda Dinámica:** Filtrado de ciudades en tiempo real mediante un repositorio de datos.
- **Navegación Fluida:** Implementación de `Jetpack Navigation` con paso de argumentos entre pantallas.
- **UI Moderna:** Diseño basado en **Material 3** con Dark Mode nativo, gradientes dinámicos y componentes personalizados.
- **Categorización:** Clasificación de lugares por tipo de plan (Comer, Noche, Visitar, Relax).

## Stack Tecnológico

- **Lenguaje:** [Kotlin](https://kotlinlang.org/)
- **UI:** [Jetpack Compose](https://developer.android.com/jetpack/compose) (Declarative UI)
- **Inyección de Dependencias:** [Hilt](https://developer.android.com/training/dependency-injection/hilt-android)
- **Arquitectura:** MVVM (Model-View-ViewModel)
- **Navegación:** Compose Navigation
- **Gestión de Estado:** StateFlow y ViewModel (Corrutinas de Kotlin)

##  Estructura del Proyecto

```text
com.example.nowlocationn
├── home            # Configuración de rutas y NavHost
├── model           # Clases de datos (Lugar)
├── repository      # Lógica de acceso a datos y sugerencias
├── ui
│   ├── screens     # SearchScreen, WheelScreen, DetallesScreen
│   └── theme       # Configuración de colores, tipografía y formas
└── viewmodel       # Lógica de negocio y gestión de estado de la UI
