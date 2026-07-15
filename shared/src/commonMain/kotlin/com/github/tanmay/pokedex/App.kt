package com.github.tanmay.pokedex

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.github.tanmay.pokedex.navigation.NavigationRoot
import com.github.tanmay.pokedex.ui.theme.PokedexTheme

@Composable
@Preview
fun App() {
    PokedexTheme(darkTheme = false) {
        NavigationRoot()
    }
}
