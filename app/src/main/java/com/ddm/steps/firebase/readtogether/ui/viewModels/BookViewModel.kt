package com.ddm.steps.firebase.readtogether.ui.viewModels

import androidx.lifecycle.ViewModel
import com.ddm.steps.firebase.readtogether.ui.states.BookUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class BookViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(BookUiState())
    val uiState = _uiState.asStateFlow()

    init {
        _uiState.update { currentState ->
            currentState.copy(
                changeBookStatus = { statusNumber ->
                    _uiState.update {
                        it.copy ( currentStatus = statusNumber )
                    }
                }
            )
        }
    }
}
