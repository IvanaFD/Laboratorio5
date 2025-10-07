package com.example.laboratorio5.ui


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.laboratorio5.data.network.Pokemon
import com.example.laboratorio5.data.repository.MainRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class StateInterface {
    object Loading : StateInterface()
    data class Success(val data: List<Pokemon>) : StateInterface()
    data class Error(val message: String) : StateInterface()
}

class MainViewModel : ViewModel() {
    private val repository = MainRepository()

    private val _uiState = MutableStateFlow<StateInterface>(StateInterface.Loading)
    val uiState: StateFlow<StateInterface> get() = _uiState

    fun getPokemonList() {
        _uiState.value = StateInterface.Loading
        viewModelScope.launch {
            val result = repository.getPokemonList()
            if (result.isSuccess) {
                _uiState.value = StateInterface.Success(result.getOrNull()?.results ?: emptyList())
            } else {
                _uiState.value = StateInterface.Error(result.exceptionOrNull()?.localizedMessage ?: "Error desconocido")
            }
        }
    }
}