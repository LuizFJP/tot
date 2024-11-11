package com.ddm.steps.firebase.readtogether.ui.viewModels

import androidx.lifecycle.ViewModel
import com.ddm.steps.firebase.readtogether.ui.states.SignInUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SignInViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(SignInUiState())
    val uiState = _uiState.asStateFlow()

    init {
        _uiState.update { currentState ->
            currentState.copy(
                onChangeEmail = { email ->
                    _uiState.update {
                        it.copy(email = email)
                    }

                },
                onChangePassword = { password ->
                    _uiState.update {
                        it.copy(password = password)
                    }
                }
            )
        }
    }
}