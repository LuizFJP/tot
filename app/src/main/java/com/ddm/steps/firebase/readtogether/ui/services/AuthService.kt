package com.ddm.steps.firebase.readtogether.ui.services

import com.ddm.steps.firebase.readtogether.ui.dao.UserDAO
import com.ddm.steps.firebase.readtogether.ui.states.SignInUiState
import com.ddm.steps.firebase.readtogether.ui.states.SignUpUiState
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

internal class AuthService {
    fun createUser(createUser: SignUpUiState, onComplete: () -> Unit) {
        Firebase.auth.createUserWithEmailAndPassword(createUser.email, createUser.password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userDAO = UserDAO()
                    task.result.user?.let { userDAO.create(it.uid) }
                    onComplete()
                } else {
                    println("deu ruim")
                };
            }
    }

    fun loginUser(user: SignInUiState, onComplete: (email: String) -> Unit, onFailure: () -> Unit) {
        Firebase.auth.signInWithEmailAndPassword(user.email, user.password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onComplete(user.email)
                } else {
                    onFailure()
                }
            }
            .addOnFailureListener { exception ->
                onFailure()
            }

    }
}
