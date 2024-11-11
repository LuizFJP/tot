package com.ddm.steps.firebase.readtogether.ui.states

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

class LibraryScreenUiState {
}

data class TabItem(
    val title: String,
    val unselectedIcon: ImageVector,
    val selectedIcon: ImageVector,
    val color: Color
)

data class Book(
    val title: String,
    val author: String,
    val imageUrl: String,
    val category: Int
)