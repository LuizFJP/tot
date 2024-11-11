package com.ddm.steps.firebase.readtogether.ui.states

data class BookUiState(
    val title: String = "",
    val author: String = "",
    val imageUrl: String = "",
    val currentStatus: Int = 0,
    val isFavorite: Int = 1,
    val isRead: Int = 2,
    val isToRead: Int = 3,
    val isReading: Int = 4,
    val isGiveUp: Int = 5,
    val changeBookStatus: (Int) -> Unit = {}
)