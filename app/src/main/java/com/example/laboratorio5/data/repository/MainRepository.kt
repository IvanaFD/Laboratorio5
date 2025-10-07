package com.example.laboratorio5.data.repository


import com.example.laboratorio5.data.network.PokeApiService
import com.example.laboratorio5.data.network.RetrofitClient
import com.example.laboratorio5.data.network.PokeResponse

class MainRepository {
    private val pokeApiService = RetrofitClient.instance.create(PokeApiService::class.java)


    suspend fun getPokemonList(): Result<PokeResponse> {
        return try {
            val response = pokeApiService.getPokemonList()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}