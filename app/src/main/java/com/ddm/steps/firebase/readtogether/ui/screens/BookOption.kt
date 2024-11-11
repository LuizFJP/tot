package com.ddm.steps.firebase.readtogether.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.ddm.steps.firebase.readtogether.ui.states.BookUiState

data class Option(val title: String, val action: () -> Unit)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookOption(
    bookUiState: BookUiState,
    onDismiss: () -> Unit,
    onFavoriteToggle: () -> Unit
) {
    val options = listOf(
        Option(if (bookUiState.isFavorite == bookUiState.currentStatus) "Remover dos favorito"  else  "Adicionar aos favoritos", onFavoriteToggle),
        Option(if (bookUiState.isRead == bookUiState.currentStatus) "Remover como lido"  else "Marcar como lido", onFavoriteToggle),
        Option(if (bookUiState.isToRead == bookUiState.currentStatus) "Remover de ler" else "Ler", onFavoriteToggle),
        Option(if (bookUiState.isReading == bookUiState.currentStatus) "Remover como lendo" else  "Lendo", onFavoriteToggle),
        Option(if (bookUiState.isGiveUp == bookUiState.currentStatus) "Retomar leitura" else "Retomar leitura", onFavoriteToggle),
    )

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(dismissOnClickOutside = true)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .background(Color(0xFF2C2F33), RoundedCornerShape(16.dp)),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD95F5F)),
                shape = RoundedCornerShape(50),
                modifier = Modifier.size(200.dp, 48.dp)
            ) {
                Text(text = "Adicionar amigo", fontSize = 16.sp, color = Color.White)
            }
            options.forEachIndexed { index, option ->
                Button(
                    onClick = { bookUiState.changeBookStatus(index + 1)},
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD95F5F)),
                    shape = RoundedCornerShape(50),
                    modifier = Modifier.size(200.dp, 48.dp)
                ) {
                    Text(text = option.title, fontSize = 16.sp, color = Color.White)
                }
            }
        }
    }
}