package com.github.tanmay.pokedex

import androidx.compose.ui.window.ComposeUIViewController
import com.github.tanmay.pokedex.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) {
    App()
}