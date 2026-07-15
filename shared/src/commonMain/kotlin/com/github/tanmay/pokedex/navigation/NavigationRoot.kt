package com.github.tanmay.pokedex.navigation

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.BottomAppBarScrollBehavior
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import com.github.tanmay.pokedex.feature.feature_feed.presentation.bookmarks.BookmarksRoot
import com.github.tanmay.pokedex.feature.feature_feed.presentation.home.HomeRoot
import com.github.tanmay.pokedex.feature.feature_settings.presentation.SettingsRoot
import com.github.tanmay.pokedex.ui.theme.PokedexRed
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import org.jetbrains.compose.resources.painterResource


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationRoot(
    modifier: Modifier = Modifier
) {
    val scrollBehavior = BottomAppBarDefaults.exitAlwaysScrollBehavior()
    val nestedScrollState = scrollBehavior.nestedScrollConnection
    val backStack = rememberNavBackStack(
        configuration = SavedStateConfiguration {
            serializersModule = SerializersModule {
                polymorphic(NavKey::class) {
                    subclass(Route.Home::class, Route.Home.serializer())
                    subclass(Route.Bookmarks::class, Route.Bookmarks.serializer())
                    subclass(Route.Settings::class, Route.Settings.serializer())
                }
            }
        },
        Route.Home
    )

    val selectedRoute by remember {
        derivedStateOf { backStack.lastOrNull() as? Route }
    }

    Scaffold(
        contentWindowInsets = WindowInsets(0),
        bottomBar = {
            BottomNavigationBar(
                selectedRoute = selectedRoute,
                onBottomNavigationItemClick = { route ->
                    if (backStack.lastOrNull() != route) {
                        // Reset to the selected top-level destination.
                        backStack.clear()
                        backStack.add(route)
                    }
                },
                scrollBehavior = scrollBehavior
            )
        },
        modifier = Modifier.nestedScroll(nestedScrollState)
    ) { innerPadding ->
        NavDisplay(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize(),
            backStack = backStack,
            entryDecorators = listOf(
                rememberSaveableStateHolderNavEntryDecorator(),
                rememberViewModelStoreNavEntryDecorator()
            ),
            entryProvider = { key ->
                when (key) {
                    is Route.Home -> NavEntry(key) { HomeRoot() }
                    is Route.Bookmarks -> NavEntry(key) { BookmarksRoot() }
                    is Route.Settings -> NavEntry(key) { SettingsRoot() }
                    else -> error("Unknown NavKey: $key")
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavigationBar(
    onBottomNavigationItemClick: (Route) -> Unit,
    selectedRoute: Route?,
    scrollBehavior: BottomAppBarScrollBehavior = BottomAppBarDefaults.exitAlwaysScrollBehavior(),
) {
    BottomAppBar(
        scrollBehavior = scrollBehavior
    ) {
        TOP_LEVEL_DESTINATION.forEach { destination ->
            NavigationBarItem(
                selected = selectedRoute == destination.route,
                onClick = { onBottomNavigationItemClick(destination.route) },
                icon = {
                    Icon(
                        painter = painterResource(destination.icon),
                        contentDescription = destination.label
                    )
                },
                label = { Text(destination.label) },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = PokedexRed.copy(0.1f),
                    selectedIconColor = PokedexRed,
                    selectedTextColor = PokedexRed
                )
            )
        }
    }
}
