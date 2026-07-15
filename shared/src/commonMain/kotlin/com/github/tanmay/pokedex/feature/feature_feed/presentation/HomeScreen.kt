package com.github.tanmay.pokedex.feature.feature_feed.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.github.tanmay.pokedex.core.domain.model.PokemonSprite
import com.github.tanmay.pokedex.feature.feature_feed.presentation.Compoents.ErrorCard
import com.github.tanmay.pokedex.feature.feature_feed.presentation.Compoents.LoadingContent
import com.github.tanmay.pokedex.feature.feature_feed.presentation.Compoents.PokemonGrid
import com.github.tanmay.pokedex.ui.theme.PokedexRed
import com.github.tanmay.pokedex.ui.theme.PokedexTheme
import org.koin.compose.viewmodel.koinViewModel



@Composable
fun HomeRoot(
    viewModel: HomeViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    HomeScreen(
        state = state
    )
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    state: HomeState,
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        containerColor = MaterialTheme.colorScheme.background,
        contentWindowInsets = WindowInsets(0),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Pokedex",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                },
                actions = {
                    IconButton(
                        onClick = {}
                    ) {

                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = PokedexRed,
                    titleContentColor = Color.White
                ),
                scrollBehavior = scrollBehavior,
                windowInsets = TopAppBarDefaults.windowInsets
            )
        }
    ) { innerPadding ->

        when (state.sprites) {
            is HomeState.ContentState.Error -> {
                ErrorCard(
                    message = state.sprites.message,
                    modifier = Modifier.padding(innerPadding)
                )
            }

            HomeState.ContentState.Loading -> {
                LoadingContent(
                    modifier = Modifier.padding(innerPadding)
                )
            }

            is HomeState.ContentState.Success -> {
                PokemonGrid(
                    pokemon = state.sprites.pokemon,
                    contentPadding = innerPadding
                )
            }
        }
    }
}


@Preview
@Composable
fun HomeScreenPreview() {
    PokedexTheme(darkTheme = false) {
        HomeScreen(
            state = HomeState(
                sprites = HomeState.ContentState.Success(List(6) { index ->
                    val id = index + 1
                    PokemonSprite(
                        id = id,
                        name = "Pokemon $id",
                        spriteUrl = ""
                    )
                })
            )
        )
    }
}
