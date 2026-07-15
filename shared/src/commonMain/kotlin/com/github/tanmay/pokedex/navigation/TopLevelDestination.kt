package com.github.tanmay.pokedex.navigation

import org.jetbrains.compose.resources.DrawableResource
import pokedex.shared.generated.resources.Res
import pokedex.shared.generated.resources.ic_favourite
import pokedex.shared.generated.resources.ic_home
import pokedex.shared.generated.resources.ic_settings

data class BottomNavItem(
    val label: String,
    val icon: DrawableResource,
    val route: Route
)

val TOP_LEVEL_DESTINATION = listOf(
    BottomNavItem(
        label = "Home",
        icon = Res.drawable.ic_home,
        route = Route.Home
    ),
    BottomNavItem(
        label = "Bookmarks",
        icon = Res.drawable.ic_favourite,
        route = Route.Bookmarks
    ),
    BottomNavItem(
        label = "Settings",
        icon = Res.drawable.ic_settings,
        route = Route.Settings
    )
)
