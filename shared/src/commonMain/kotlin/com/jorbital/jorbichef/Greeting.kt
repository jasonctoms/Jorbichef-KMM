package com.jorbital.jorbichef

class Greeting(private val platform: Platform) {
    fun greet(): String {
        return "Hello, ${platform.name}!"
    }
}