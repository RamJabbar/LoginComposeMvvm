package com.example.logincomposefirebase.data.repository

import com.google.firebase.auth.FirebaseAuth

class AuthRepository {
    private val auth = FirebaseAuth.getInstance()

    fun login(
        email: String,
        password: String,
        result: (Boolean, String?) -> Unit
    ) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                result(it.isSuccessful, it.exception?.message)
            }
    }

    fun register(
        email: String,
        password: String,
        result: (Boolean, String?) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                result(it.isSuccessful, it.exception?.message)
            }
    }

    fun logout() {
        auth.signOut()
    }
    fun isLoggedIn(): Boolean {
        return auth.currentUser != null
    }
}
