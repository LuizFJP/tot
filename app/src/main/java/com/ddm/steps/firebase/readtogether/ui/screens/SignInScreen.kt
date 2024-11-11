package com.ddm.steps.firebase.readtogether.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ddm.steps.firebase.readtogether.ui.states.SignInUiState
import com.ddm.steps.firebase.readtogether.ui.theme.DarkBlue
import com.ddm.steps.firebase.readtogether.ui.theme.Pink
import com.ddm.steps.firebase.readtogether.ui.viewModels.SignInViewModel

@Preview
@Composable
fun SignInScreenPreview() {
    val viewModel: SignInViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState()
    val signInClickMock: (SignInUiState) -> Unit = {}
    val signUpClickMock: () -> Unit = {}
    SignInScreen(signInClickMock, signUpClickMock, uiState)
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SignInScreen(
    onSignInClick: (SignInUiState) -> Unit,
    onSignUpClick: () -> Unit,
    signInUiState: SignInUiState
) {
    Scaffold(
        containerColor = DarkBlue,
    ) {
        Column(Modifier.padding(top = 200.dp, start = 30.dp, end = 30.dp)) {
            TextField(
                value = signInUiState.email,
                onValueChange = signInUiState.onChangeEmail,
                modifier = Modifier
                    .fillMaxWidth(),
                label = {
                    Text("Email")
                },
                shape = RoundedCornerShape(16.dp),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.White
                )
            )
            Spacer(modifier = Modifier.size(50.dp))
            TextField(
                value = signInUiState.password,
                onValueChange = signInUiState.onChangePassword,
                modifier = Modifier
                    .fillMaxWidth(),
                label = {
                    Text("Senha")
                },
                shape = RoundedCornerShape(16.dp),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.White
                )
            )
            Spacer(modifier = Modifier.size(20.dp))

            Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = { onSignInClick(signInUiState) },
                    modifier = Modifier
                        .padding(11.dp)
                        .size(width = 120.dp, height = 40.dp),
                    colors = ButtonDefaults.buttonColors(Pink),
                ) {
                    Text(text = "Entrar")
                }
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = onSignUpClick,
                    modifier = Modifier.padding(8.dp),
                    colors = ButtonDefaults.buttonColors(Pink)
                ) {
                    Text(text = "Cadastrar")
                }
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}