package com.example.laboratorio5

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.laboratorio5.network.PokeApiService
import com.example.laboratorio5.network.RetrofitClient
import com.example.laboratorio5.ui.theme.Laboratorio5Theme
import kotlinx.coroutines.launch
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.ui.layout.ContentScale
import com.example.laboratorio5.network.Pokemon

import java.util.Locale
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack




private const val TAG = "Pokedex-App"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Laboratorio5Theme {
                PokeApp()
            }
        }


    }
}
/*
* Inicio de la app con su navegacion, asi empieza con la pantalla con el listado de pokemones
* */
@Composable
fun PokeApp() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "list") {
        composable("list") {
            PokeListScreen(navController)
        }
        composable("detail/{name}/{id}") { backStackEntry ->
            val name = backStackEntry.arguments?.getString("name") ?: ""
            val id = backStackEntry.arguments?.getString("id") ?: ""
            val url = "https://pokeapi.co/api/v2/pokemon/$id/"
            val pokemon = Pokemon(name = name, url = url)
            PokeDetailScreen(pokemon,navController)
        }
    }
}
/*
* Pantalla con el listado de pokemones
* */
@Composable
fun PokeListScreen(navController: NavHostController) {

    val pokemonList = remember { mutableStateOf<List<Pokemon>>(emptyList()) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            try {
                val pokeApiService = RetrofitClient.instance.create(PokeApiService::class.java)
                val response = pokeApiService.getPokemonList()
                pokemonList.value = response.results
            } catch (e: Exception) {

                e.printStackTrace()
            }
        }
    }
    Surface (
        modifier = Modifier.fillMaxSize()
    ){
        Column(
            modifier = Modifier.fillMaxWidth().background(Color(0xFFCCE7FF)),


        ){
            Image(painterResource(R.drawable.logopokedex),
                contentDescription = "Logo de la Pokemon",
                modifier = Modifier.requiredSize(200.dp).align(CenterHorizontally)
            )
            LazyColumn(
                modifier = Modifier.fillMaxWidth().background(Color(0xFFFAFFFE)),
                contentPadding = PaddingValues(vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(pokemonList.value) { pokemon ->
                    PokeCard(pokemon, navController)
                }
            }
        }
    }
}
/*
* Card de cada pokemon que aparece en la lisat de pokemones en la primera pantalla asi es clicleable para entrar a ver sus detalles
* */
@Composable
fun PokeCard(pokemon: Pokemon, navController: NavHostController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {

                navController.navigate("detail/${pokemon.name}/${pokemon.id}")
            },
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFC6C4FF)
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {

            Image(
                painter = rememberAsyncImagePainter(model = pokemon.imageUrlFront),
                contentDescription = pokemon.name,
                modifier = Modifier
                    .size(64.dp)
                    .padding(end = 16.dp),
                contentScale = ContentScale.Crop
            )
            Text(
                text = pokemon.name.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() },
                style = MaterialTheme.typography.headlineSmall
            )
        }
    }
}

 /*
 * Pantalla con imagen de frente y atras de su version normal y shiny
 * */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokeDetailScreen(pokemon: Pokemon, navController: NavHostController) {
    Scaffold(
        containerColor = Color(0xFFFAFFFE),
        topBar = {
            CenterAlignedTopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Regresar"
                        )
                    }
                },
                title = {
                    Text(
                        text = pokemon.name.replaceFirstChar { it.uppercase() },
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFFE5FFF9)
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.normal_label),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFCCE7FF)),
                textAlign = TextAlign.Center
            )
            Row(
                modifier = Modifier.fillMaxWidth().background(Color(0xFFE5FFFA)),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.Top
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = stringResource(R.string.front_label),
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Image(
                        painter = rememberAsyncImagePainter(model = pokemon.imageUrlFront),
                        contentDescription = null,
                        modifier = Modifier.size(200.dp)
                    )
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {

                    Text(
                        text = stringResource(R.string.back_label),
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Image(
                        painter = rememberAsyncImagePainter(model = pokemon.imageUrlBack),
                        contentDescription = null,
                        modifier = Modifier.size(200.dp)
                    )
                }
            }
            Text(
                text = stringResource(R.string.shiny_label),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFCCE7FF)),
                textAlign = TextAlign.Center
            )
            Row(
                modifier = Modifier.fillMaxWidth().background(Color(0xFFE5FFFA)),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.Top
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {

                    Text(
                        text = stringResource(R.string.shiny_front_label),
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Image(
                        painter = rememberAsyncImagePainter(model = pokemon.imageUrlShinyFront),
                        contentDescription = null,
                        modifier = Modifier.size(200.dp)
                    )
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = stringResource(R.string.shiny_back_label),
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Image(
                        painter = rememberAsyncImagePainter(model = pokemon.imageUrlShinyBack),
                        contentDescription = null,
                        modifier = Modifier.size(200.dp)
                    )
                }
            }
        }
    }
}




