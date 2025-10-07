# Laboratorio 5 - Pokedex con Retrofit 

## Descripción
Este laboratorio consistió en desarrollar una aplicación móvil en Android que consume la API pública de [PokeAPI](https://pokeapi.co/).  
El objetivo fue poner en práctica el uso de **permisos y acceso a internet con Retrofit**.

La aplicación cuenta con dos pantallas principales:
1. **Listado de Pokémon**: se muestran los primeros 100 Pokémon obtenidos desde la API.  
2. **Detalle del Pokémon**: al seleccionar un Pokémon de la lista, se despliega una vista con 4 imágenes diferentes: frente y atrás en su versión normal, y frente y atrás en su versión shiny.

## Uso de Retrofit
Para la comunicación con la API se configuró Retrofit de la siguiente forma:
- Se definió un **cliente Retrofit** (`RetrofitClient`) con la URL base de la API.  
- Se creó una **interfaz de servicio** (`PokeApiService`) donde se especificó el endpoint para obtener la lista de los 100 Pokémon.  
- Se implementó un **data class** (`PokeResponse` y `Pokemon`) para mapear la respuesta JSON y facilitar el acceso a los datos y a las URLs de las imágenes.  

De esta forma, la aplicación puede obtener los datos en segundo plano y mostrarlos en la UI construida con Jetpack Compose.

## Tecnologías utilizadas
- **Kotlin**  
- **Retrofit** para consumo de la API  
- **Gson** como convertidor de JSON  
- **Jetpack Compose** para la interfaz gráfica  
- **Navigation Compose** para la navegación entre pantallas  

## Estructura general
- **MainActivity**: inicializa la app y configura la navegación.  
- **PokeListScreen**: muestra el listado de Pokémon usando Retrofit para cargar los datos.  
- **PokeDetailScreen**: despliega las imágenes del Pokémon seleccionado en sus diferentes versiones.  

## Vista de la App

![Lista de pokemones](https://github.com/IvanaFD/Laboratorio5/blob/main/vistaLista.png?raw=true)

![Detalles de Pokemones](https://github.com/IvanaFD/Laboratorio5/blob/main/VistaDetalles.png?raw=true)

# Laboratorio 6 - Refactorización con MVVM y Clean Architecture
En este laboratorio se reorganizó el proyecto para seguir el patrón **MVVM** y una arquitectura limpia simplificada.  
Se agregó un **ViewModel** que maneja el estado de la UI y la lógica de presentación usando StateFlow, y un **Repository** que actúa como intermediario entre la capa de datos (Retrofit) y el ViewModel.  
La app ahora maneja estados como *cargando* y *error* de forma reactiva, mostrando una imagen de carga cuando se están obteniendo los datos y un mensaje de error (con opción de reintentar) si la llamada falla, por ejemplo al desconectar el wifi.


**Pantalla de carga:**  
![Ejemplo cargando](https://github.com/IvanaFD/Laboratorio5/blob/main/VistaCargando.png?raw=true)

**Pantalla de error:**  
![Ejemplo error](https://github.com/IvanaFD/Laboratorio5/blob/main/VistaReintentar.png?raw=true)


