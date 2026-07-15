package com.github.tanmay.pokedex.feature.feature_feed.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.tanmay.pokedex.core.domain.model.PokemonSprite
import com.github.tanmay.pokedex.core.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val pokemonRepository: PokemonRepository
) : ViewModel() {

    private val _state = MutableStateFlow<HomeState>(HomeState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    sprites = HomeState.ContentState.Loading
                )
            }
            val sprites = pokemonRepository.getPokemon()




            if (sprites.isSuccess && sprites.getOrNull() != null) {
                _state.update {
                    it.copy(
                        sprites = HomeState.ContentState.Success(sprites.getOrNull()!!)
                    )
                }
            }

            if (sprites.isFailure) {
                _state.update {
                    it.copy(
                        sprites = HomeState.ContentState.Error(
                            (sprites.exceptionOrNull() ?: "Failed to load").toString()
                        )
                    )
                }
            }


        }
    }
}


data class HomeState(
    val sprites: ContentState = ContentState.Loading,
    val isRefreshing: Boolean = false
) {
    sealed interface ContentState {
        data object Loading : ContentState
        data class Success(val pokemon: List<PokemonSprite>) : ContentState
        data class Error(val message: String) : ContentState
    }
}
