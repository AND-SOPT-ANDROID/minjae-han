package org.sopt.and.presentation.signin

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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import org.sopt.and.data.local.AuthLocalDataSource
import org.sopt.and.presentation.component.PasswordInputField
import org.sopt.and.presentation.component.SignBottomBox
import org.sopt.and.presentation.component.TextInputField

@Composable
fun SignInScreen(
    signInViewModel: SignInViewModel = viewModel(
        factory = SignInViewModel.provideFactory(
            AuthLocalDataSource.getInstance(LocalContext.current)
        )
    ),
    modifier: Modifier = Modifier,
    onLoginSuccess: () -> Unit = {},
    onSignUpClick: () -> Unit = {}
) {
    val uiState by signInViewModel.uiState.collectAsState()
    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(uiState.errorMessage) {
        uiState.errorMessage?.let { message ->
            snackBarHostState.showSnackbar(message)
        }
    }

    LaunchedEffect(uiState.token) {
        uiState.token?.let {
            onLoginSuccess()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
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

            TextInputField(
                value = uiState.username,
                onValueChange = { signInViewModel.onUsernameChange(it) },
                placeholder = "username",
                isError = uiState.errorMessage?.contains("username") == true
            )

            Spacer(modifier = Modifier.height(5.dp))

            PasswordInputField(
                value = uiState.password,
                onValueChange = { signInViewModel.onPasswordChange(it) },
                showPassword = uiState.showPassword,
                placeholder = "password",
                onVisibilityChange = { signInViewModel.onPasswordVisibilityChange() },
                isError = uiState.errorMessage?.contains("비밀번호") == true
            )

            Spacer(modifier = Modifier.height(30.dp))

            Button(
                onClick = { signInViewModel.signIn() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Blue,
                    contentColor = Color.White
                ),
                enabled = !uiState.isLoading &&
                        uiState.username.isNotBlank() &&
                        uiState.password.isNotBlank()
            ) {
                Text(text = "로그인", fontSize = 15.sp)
            }

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

            Spacer(modifier = Modifier.height(20.dp))

            SignBottomBox()
        }

        if (uiState.isLoading) {
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