package org.sopt.and.presentation.auth.signin

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import org.sopt.and.presentation.component.PasswordInputField
import org.sopt.and.presentation.component.SignBottomBox
import org.sopt.and.presentation.component.TextInputField

@Composable
fun SignInScreen(
    viewModel: SignInViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
    onLoginSuccess: () -> Unit = {},
    onSignUpClick: () -> Unit = {}
) {
    val state by viewModel.state.collectAsState()
    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    // Effect handling
    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is SignInEffect.ShowError -> {
                    snackBarHostState.showSnackbar(effect.message)
                }
                SignInEffect.NavigateToHome -> onLoginSuccess()
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
                Icon(
                    modifier = Modifier
                        .size(35.dp)
                        .clickable { },
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = "뒤로가기",
                    tint = Color.White
                )
                Text(
                    modifier = Modifier
                        .weight(1f)
                        .align(Alignment.CenterVertically),
                    fontWeight = FontWeight.Bold,
                    text = "Wavve",
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    fontSize = 30.sp,
                )
                Spacer(modifier = Modifier.size(35.dp))
            }

            Spacer(modifier = Modifier.height(30.dp))

            // Input fields
            TextInputField(
                value = state.username,
                onValueChange = { viewModel.processIntent(SignInIntent.UpdateUsername(it)) },
                placeholder = "username"
            )

            Spacer(modifier = Modifier.height(5.dp))

            PasswordInputField(
                value = state.password,
                onValueChange = { viewModel.processIntent(SignInIntent.UpdatePassword(it)) },
                showPassword = state.showPassword,
                placeholder = "password",
                onVisibilityChange = { viewModel.processIntent(SignInIntent.TogglePasswordVisibility) }
            )

            Spacer(modifier = Modifier.height(30.dp))

            // Login button
            Button(
                onClick = { viewModel.processIntent(SignInIntent.SignIn) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Blue,
                    contentColor = Color.White
                ),
                enabled = !state.isLoading &&
                        state.username.isNotBlank() &&
                        state.password.isNotBlank()
            ) {
                Text(text = "로그인", fontSize = 15.sp)
            }

            // Navigation links
            Spacer(modifier = Modifier.height(30.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = "아이디 찾기",
                    color = Color.Gray,
                    modifier = Modifier.clickable { }
                )
                Text(text = "|", color = Color.Gray)
                Text(
                    text = "비밀번호 재설정",
                    color = Color.Gray,
                    modifier = Modifier.clickable { }
                )
                Text(text = "|", color = Color.Gray)
                Text(
                    text = "회원 가입",
                    color = Color.Gray,
                    modifier = Modifier.clickable { onSignUpClick() }
                )
            }

            Spacer(modifier = Modifier.height(55.dp))
            SignBottomBox()
        }

        if (state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                color = Color.White
            )
        }
    }

    SnackbarHost(
        hostState = snackBarHostState,
        modifier = Modifier.padding(16.dp)
    )
}

@Preview
@Composable
fun SignInScreenPreview() {
    SignInScreen()
}
