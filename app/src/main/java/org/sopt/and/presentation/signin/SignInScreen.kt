package org.sopt.and.presentation.signin

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import kotlinx.coroutines.launch
import org.sopt.and.presentation.component.ActionText
import org.sopt.and.presentation.component.EmailInputField
import org.sopt.and.presentation.component.PasswordInputField
import org.sopt.and.presentation.component.SignBottomBox

@Composable
fun SignInScreen(
    signInViewModel: SignInViewModel = viewModel(),
    modifier: Modifier = Modifier,
    email: String = "",
    password: String = "",
    navigateToSignUp: () -> Unit = {},
    navigateToMyPage: (String) -> Unit = {}
) {
    val uiState by signInViewModel.uiState.collectAsState()
    val context = LocalContext.current
    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(email, password) {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            signInViewModel.updateRegisteredUser(email, password)
        }
    }

    LaunchedEffect(uiState.errorMessage) {
        uiState.errorMessage?.let { message ->
            snackBarHostState.showSnackbar(message)
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

            EmailInputField(
                value = uiState.email,
                onValueChange = { signInViewModel.onEmailChange(it) },
                placeholder = "이메일 주소 또는 아이디",
                isError = uiState.errorMessage?.contains("이메일") == true
            )

            Spacer(modifier = Modifier.height(5.dp))

            PasswordInputField(
                value = uiState.password,
                onValueChange = { signInViewModel.onPasswordChange(it) },
                showPassword = uiState.showPassword,
                placeholder = "비밀번호",
                onVisibilityChange = { signInViewModel.onPasswordVisibilityChange() },
                isError = uiState.errorMessage?.contains("비밀번호") == true
            )

            Spacer(modifier = Modifier.height(30.dp))

            Button(
                onClick = {
                    scope.launch {
                        if (signInViewModel.signIn(uiState.email, uiState.password)) {
                            snackBarHostState.showSnackbar("로그인 성공!")
                            navigateToMyPage(uiState.email)
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Blue,
                    contentColor = Color.White
                )
            ) {
                Text(text = "로그인", fontSize = 15.sp)
            }

            Spacer(modifier = Modifier.height(30.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ActionText(
                    text = "아이디 찾기",
                    nextScreen = { }
                )
                Text(text = "|", color = Color.Gray)
                ActionText(
                    text = "비밀번호 재설정",
                    nextScreen = { }
                )
                Text(text = "|", color = Color.Gray)
                ActionText(
                    text = "회원 가입",
                    nextScreen = { navigateToSignUp() }
                )
            }

            Spacer(modifier = Modifier.height(55.dp))

            SignBottomBox()
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