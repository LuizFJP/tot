package com.ddm.steps.firebase.readtogether.ui.states

data class SignInUiState (
    val email: String = "",
    val onChangeEmail: (String) -> Unit = {},
    val password: String = "",
    val onChangePassword: (String) -> Unit = {},
    )