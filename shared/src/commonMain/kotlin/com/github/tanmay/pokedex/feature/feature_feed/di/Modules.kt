package com.github.tanmay.pokedex.feature.feature_feed.di

import com.github.tanmay.pokedex.feature.feature_feed.presentation.HomeViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module


val feedModule = module {
    viewModelOf(::HomeViewModel)
}
