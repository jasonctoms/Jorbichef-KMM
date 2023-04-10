package com.jorbital.jorbichef

expect class Platform() {
    val name: String
    fun uuid(): String
}