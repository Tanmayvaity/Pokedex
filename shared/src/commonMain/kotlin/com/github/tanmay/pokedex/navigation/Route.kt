package com.github.tanmay.pokedex.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

sealed interface Route : NavKey {
    @Serializable
    data object Home : Route

    @Serializable
    data object Search : Route

    @Serializable
    data object Bookmarks : Route

    @Serializable
    data object Settings : Route

}