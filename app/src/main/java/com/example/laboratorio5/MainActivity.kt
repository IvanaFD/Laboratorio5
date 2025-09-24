package com.example.laboratorio5

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.example.laboratorio5.network.PokeApiService
import com.example.laboratorio5.network.PokeResponse
import com.example.laboratorio5.network.RetrofitClient
import com.example.laboratorio5.ui.theme.Laboratorio5Theme
import kotlinx.coroutines.launch
import okhttp3.Call
import okhttp3.Response
import javax.security.auth.callback.Callback





class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val api = RetrofitClient.instance.create(PokeApiService::class.java)

        lifecycleScope.launch {
            try {
                val response = api.getPokemonList()
                Log.d("POKEMON", "Cantidad de pokémon: ${response.results.size}")

                // Mostramos los primeros 10 en el Logcat
                response.results.take(10).forEach {
                    Log.d("POKEMON", "Nombre: ${it.name} | ID: ${it.id}")
                }

            } catch (e: Exception) {
                Log.e("POKEMON", "Error: ${e.message}")
            }
        }
    }
}