package com.example.laboratorio5.data.network

import retrofit2.http.GET

interface PokeApiService {
    @GET("pokemon?limit=100&offset=0")
    suspend fun getPokemonList(): PokeResponse
}