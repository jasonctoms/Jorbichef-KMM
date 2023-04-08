package com.jorbital.jorbichef.auth

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.FirebaseUser
import dev.gitlive.firebase.auth.auth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class JorbichefAuth {
    private val auth by lazy { Firebase.auth }
    private val currentUser: FirebaseUser?
        get() = auth.currentUser

    private val _loginState = MutableStateFlow(
        when (currentUser?.isAnonymous) {
            true -> LoginState.ANONYMOUS
            false -> LoginState.LOGGED_IN
            else -> LoginState.LOGGED_OUT
        }
    )
    val loginState = _loginState.asStateFlow()

    suspend fun signInAnonymously(): Result<String?> {
        return try {
            if (currentUser == null) {
                auth.signInAnonymously()
                _loginState.value = LoginState.ANONYMOUS
            }
            Result.success(currentUser?.uid)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun assertUserId(): String {
        if (currentUser?.uid != null) {
            return currentUser!!.uid
        }
        throw NotLoggedInException()
    }
}

enum class LoginState { ANONYMOUS, LOGGED_IN, LOGGED_OUT }

class NotLoggedInException : Exception("No user uid from firebase found!")