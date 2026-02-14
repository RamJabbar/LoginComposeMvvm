package com.example.logincomposefirebase.viewmodel

import androidx.lifecycle.ViewModel
import com.example.logincomposefirebase.data.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow



class AuthViewModel: ViewModel(){

    sealed class AuthState{
        object Idle : AuthState()
        object Loading : AuthState()
        data class Success(val message: String) : AuthState()
        data class Error(val message: String) : AuthState()
    }

    private val auth = FirebaseAuth.getInstance()
    private val repository = AuthRepository()
    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState: StateFlow<AuthState> = _authState

    fun login(email: String, password: String) {
        repository.login(email, password) { success, message ->
            if (success) {
                _authState.value = AuthState.Success("Login Berhasil")
            } else {
                _authState.value =  AuthState.Error(message ?: "Login Gagal")
            }
        }
    }
    fun register(email: String, password: String, confirmPassword: String) {
        repository.register(email, password) { success, message ->
            if (success) {
                _authState.value = AuthState.Success("Register Berhasil")
            } else {
                _authState.value = AuthState.Error(message ?: "Register Gagal")
            }
        }
    }

    fun logout() {
        repository.logout()
        _authState.value = AuthState.Idle

    }
    fun isLoggedIn(): Boolean {
        return repository.isLoggedIn()
    }
    fun resetAuthState() {
        _authState.value = AuthState.Idle
    }
}



