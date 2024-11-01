package org.sopt.and.presentation.signup

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
import androidx.lifecycle.viewmodel.compose.viewModel
import org.sopt.and.domain.User
import org.sopt.and.presentation.component.EmailInputField
import org.sopt.and.presentation.component.PasswordInputField
import org.sopt.and.presentation.component.SignBottomBox

@Composable
fun SignUpScreen(
    signUpViewModel: SignUpViewModel = viewModel(),
    modifier: Modifier = Modifier,
    navigateToSignIn: (User) -> Unit = {},
    onNavigateBack: () -> Unit = {}
) {
    val uiState by signUpViewModel.uiState.collectAsState()
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.errorMessage) {
        uiState.errorMessage?.let { message ->
            snackbarHostState.showSnackbar(message)
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
                        .clickable { onNavigateBack() },
                    imageVector = Icons.Default.Close,
                    contentDescription = "닫기",
                    tint = Color.White
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "이메일과 비밀번호 만으로\nWavve를 즐길 수 있어요!",
                fontSize = 20.sp,
                color = Color.White,
            )

            Spacer(modifier = Modifier.height(25.dp))

            EmailInputField(
                value = uiState.email,
                onValueChange = { signUpViewModel.onEmailChange(it) },
                placeholder = "wavve@example.com",
                isError = uiState.errorMessage?.contains("이메일") == true
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "! 로그인, 비밀번호 찾기, 알림에 사용되니 정확한 이메일을\n입력해 주세요.",
                color = Color.Gray,
            )

            Spacer(modifier = Modifier.height(20.dp))

            PasswordInputField(
                value = uiState.password,
                onValueChange = { signUpViewModel.onPasswordChange(it) },
                showPassword = uiState.showPassword,
                placeholder = "wavve 비밀번호 설정",
                onVisibilityChange = { signUpViewModel.onPasswordVisibilityChange() },
                isError = uiState.errorMessage?.contains("비밀번호") == true
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "! 비밀번호는 8-20자 이내, 영문 대소문자, 숫자, 특수문자 중\n3가지 이상 혼용하여 입력해 주세요.",
                color = Color.Gray,
            )

            Spacer(modifier = Modifier.height(10.dp))

            SignBottomBox()
        }

        Button(
            onClick = {
                if (signUpViewModel.onSignUpClick()) {
                    Toast.makeText(context, "회원가입이 완료되었습니다!", Toast.LENGTH_SHORT).show()
                    navigateToSignIn(signUpViewModel.getUser())
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Blue,
                contentColor = Color.White
            ),
            shape = RectangleShape,
            enabled = uiState.email.isNotBlank() && uiState.password.isNotBlank()
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