package com.github.tanmay.pokedex

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform