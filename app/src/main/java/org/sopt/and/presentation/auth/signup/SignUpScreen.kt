package org.sopt.and.presentation.auth.signup

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import org.sopt.and.presentation.component.PasswordInputField
import org.sopt.and.presentation.component.SignBottomBox
import org.sopt.and.presentation.component.TextInputField

@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
    onSignUpSuccess: () -> Unit = {},
    onBackClick: () -> Unit = {}
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }

    // Effect handling
    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is SignUpEffect.ShowError -> {
                    snackbarHostState.showSnackbar(effect.message)
                }
                SignUpEffect.NavigateToSignIn -> onSignUpSuccess()
                SignUpEffect.ShowSignUpSuccess -> {
                    Toast.makeText(context, "회원가입이 완료되었습니다!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Spacer(modifier = Modifier.size(30.dp))

                Text(
                    text = "회원가입",
                    color = Color.White,
                    fontSize = 20.sp,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
                Icon(
                    modifier = Modifier
                        .size(30.dp)
                        .clickable { onBackClick() },
                    imageVector = Icons.Default.Close,
                    contentDescription = "닫기",
                    tint = Color.White
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "아이디와 비밀번호, 취미를 입력하여\nWavve를 즐길 수 있어요!",
                fontSize = 23.sp,
                color = Color.White,
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Input fields
            TextInputField(
                value = state.username,
                onValueChange = { viewModel.processIntent(SignUpIntent.UpdateUsername(it)) },
                placeholder = "username (8자 이하)",
                maxLength = 8
            )

            Spacer(modifier = Modifier.height(20.dp))

            PasswordInputField(
                value = state.password,
                onValueChange = { viewModel.processIntent(SignUpIntent.UpdatePassword(it)) },
                showPassword = state.showPassword,
                placeholder = "password (8자 이하)",
                onVisibilityChange = { viewModel.processIntent(SignUpIntent.TogglePasswordVisibility) }
            )

            Spacer(modifier = Modifier.height(20.dp))

            TextInputField(
                value = state.hobby,
                onValueChange = { viewModel.processIntent(SignUpIntent.UpdateHobby(it)) },
                placeholder = "hobby (8자 이하)",
                maxLength = 8
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "! 모든 입력값은 8자 이하여야 합니다",
                color = Color.Gray,
            )

            SignBottomBox()
        }

        if (state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                color = Color.White
            )
        }

        Button(
            onClick = { viewModel.processIntent(SignUpIntent.SignUp) },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Blue,
                contentColor = Color.White
            ),
            shape = RectangleShape,
            enabled = !state.isLoading &&
                    state.username.isNotBlank() &&
                    state.password.isNotBlank() &&
                    state.hobby.isNotBlank()
        ) {
            Text("Wavve 회원가입", fontSize = 17.sp)
        }
    }

    SnackbarHost(
        hostState = snackbarHostState,
        modifier = Modifier.padding(16.dp)
    )
}

@Preview
@Composable
private fun SignUpScreenPreview() {
    SignUpScreen()
}
