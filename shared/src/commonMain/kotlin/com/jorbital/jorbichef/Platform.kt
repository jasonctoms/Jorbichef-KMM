package com.jorbital.jorbichef

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform