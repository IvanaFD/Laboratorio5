package com.example.laboratorio5

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.laboratorio5.network.PokeApiService
import com.example.laboratorio5.network.PokeResponse
import com.example.laboratorio5.network.Pokemon
import com.example.laboratorio5.network.RetrofitClient
import com.example.laboratorio5.ui.theme.Laboratorio5Theme
import kotlinx.coroutines.launch
import okhttp3.Call
import okhttp3.Response
import java.util.Locale
import javax.security.auth.callback.Callback


private const val TAG = "Pokedex-App"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val api = RetrofitClient.instance.create(PokeApiService::class.java)

        lifecycleScope.launch {
            try {
                val response = api.getPokemonList()
                Log.d(TAG, "Cantidad de pokémon: ${response.results.size}")
                response.results.take(100).forEach {
                    Log.d(TAG, "Nombre: ${it.name} | ID: ${it.id}")
                }

            } catch (e: Exception) {
                Log.e(TAG, "Error: ${e.message}")
            }
        }
        setContent {
            Laboratorio5Theme {
                PokeApp()
            }
        }


    }
}
@Composable
fun PokeApp() {

}
@Composable
fun PokeListScreen(navController: NavHostController) {

}

@Composable
fun PokeCardScreen(pokemon: Pokemon, navController: NavHostController){

}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokeDetailScreen(pokemon: Pokemon) {
    Scaffold(
        containerColor = Color(0xFFFAFFFE),
        topBar = {
            CenterAlignedTopAppBar(
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
                verticalAlignment = Alignment.Top // Importante para alinear las Columnas desde arriba
            ) {
                // Imagen Frontal Normal con su título
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = stringResource(R.string.front_label), // "Frente"
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Image(
                        painter = rememberAsyncImagePainter(model = pokemon.imageUrlFront),
                        contentDescription = stringResource(R.string.front_label), // Usa el string para accesibilidad también
                        modifier = Modifier.size(200.dp)
                    )


                }

                // Imagen Trasera Normal con su título
                Column(horizontalAlignment = Alignment.CenterHorizontally) {

                    Text(
                        text = stringResource(R.string.back_label), // "Atrás"
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Image(
                        painter = rememberAsyncImagePainter(model = pokemon.imageUrlBack),
                        contentDescription = stringResource(R.string.back_label),
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
                        text = stringResource(R.string.shiny_front_label), // "Shiny Frente"
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Image(
                        painter = rememberAsyncImagePainter(model = pokemon.imageUrlShinyFront),
                        contentDescription = stringResource(R.string.shiny_front_label),
                        modifier = Modifier.size(200.dp)
                    )

                }

                // Imagen Trasera Shiny con su título
                Column(horizontalAlignment = Alignment.CenterHorizontally) {


                    Text(
                        text = stringResource(R.string.shiny_back_label), // "Shiny Atrás"
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Image(
                        painter = rememberAsyncImagePainter(model = pokemon.imageUrlShinyBack),
                        contentDescription = stringResource(R.string.shiny_back_label),
                        modifier = Modifier.size(200.dp)
                    )

                }
            }
        }
    }
}




@Preview(showBackground = true)
@Composable
fun PokeAppPreview() {
    Laboratorio5Theme {
        PokeApp()
    }
}