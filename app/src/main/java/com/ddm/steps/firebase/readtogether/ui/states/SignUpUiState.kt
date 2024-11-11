package com.ddm.steps.firebase.readtogether.ui.states

data class SignUpUiState (
    val email: String = "",
    val onChangeEmail: (String) -> Unit = {},
    val password: String = "",
    val onChangePassword: (String) -> Unit = {},
    val confirmPassword: String = "",
    val onChangeConfirmPassword: (String) -> Unit = {},
    )