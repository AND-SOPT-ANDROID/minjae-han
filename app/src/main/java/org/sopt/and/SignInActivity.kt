package org.sopt.and

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import org.sopt.and.ui.theme.ANDANDROIDTheme


class SignInActivity : ComponentActivity() {
    private var registeredEmail: String? = null
    private var registeredPassword: String? = null

    private val signUpResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                result.data?.let { intent ->
                    registeredEmail = intent.getStringExtra("email")
                    registeredPassword = intent.getStringExtra("password")
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ANDANDROIDTheme {
                val snackbarHostState = remember { SnackbarHostState() }
                val scope = rememberCoroutineScope()

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    snackbarHost = { SnackbarHost(snackbarHostState) }
                ) { innerPadding ->
                    SignInScreen(
                        modifier = Modifier.padding(innerPadding),
                        onSignUpClick = {
                            val intent = Intent(this, SignUpActivity::class.java)
                            signUpResultLauncher.launch(intent)
                        },
                        onSignInAttempt = { email, password ->
                            if (email == registeredEmail && password == registeredPassword) {
                                // 로그인 성공 시 MyActivity로 이메일을 전달하며 화면 전환
                                val myActivityIntent = Intent(this, MyActivity::class.java).apply {
                                    putExtra("email", registeredEmail) // 이메일 전달
                                }
                                startActivity(myActivityIntent)
                                // 로그인 성공 Snackbar 표시
                                scope.launch {
                                    snackbarHostState.showSnackbar("로그인 성공!")
                                }
                            } else {
                                // 로그인 실패 Snackbar 표시
                                scope.launch {
                                    snackbarHostState.showSnackbar("로그인 실패. 정보를 확인해주세요.")
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun SignInScreen(
    modifier: Modifier = Modifier,
    onSignUpClick: () -> Unit,
    onSignInAttempt: (String, String) -> Unit
) {
    var emailText by remember { mutableStateOf("") }
    var passwordText by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black)
            .padding(20.dp)
    ) {
        // 상단바
        TopBar()

        Spacer(modifier = Modifier.height(30.dp))

        // 아이디 입력 필드
        InputField(
            value = remember { mutableStateOf(emailText) },
            placeholder = "이메일 주소 또는 아이디",
            onValueChange = { emailText = it }
        )

        Spacer(modifier = Modifier.height(5.dp))

        // 비밀번호 입력 필드
        PasswordField(
            value = passwordText,
            onValueChange = { passwordText = it },
            showPassword = showPassword,
            onVisibilityChange = { showPassword = !showPassword }
        )

        Spacer(modifier = Modifier.height(30.dp))

        // 로그인 버튼
        SignInButton(text = "로그인", onClick = { onSignInAttempt(emailText, passwordText) })

        Spacer(modifier = Modifier.height(30.dp))

        // 아이디 찾기, 비번 재설정, 회원가입 버튼
        ActionLinks(onSignUpClick = onSignUpClick)

        Spacer(modifier = Modifier.height(55.dp))

        Text(
            text = "또는 다른 서비스 계정으로 가입",
            color = Color.White,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        // SNS 버튼들
        Spacer(modifier = Modifier.height(20.dp))

        SNSButtons()

        Spacer(modifier = Modifier.height(15.dp))

        // 주의사항 텍스트
        Text(
            text = "• SNS계정으로 간편하게 가입하여 서비스를 이용하실 수 있습니다. 기존 POOQ 계정 또는 Wavve 계정과는 연동되지 않으니 이용에 참고하세요.",
            color = Color.Gray,
        )
    }
}

@Composable
fun TopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Icon(
            modifier = Modifier
                .size(35.dp)
                .clickable {  },
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
}

@Composable
fun InputField(value: MutableState<String>, placeholder: String, onValueChange: (String) -> Unit) {
    TextField(
        value = value.value,
        onValueChange = { newText -> value.value = newText; onValueChange(newText) },
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        placeholder = { Text(text = placeholder, color = Color.Gray) },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.DarkGray,
            unfocusedContainerColor = Color.DarkGray,
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        shape = RoundedCornerShape(5.dp),
        singleLine = true
    )
}

@Composable
fun PasswordField(
    value: String,
    onValueChange: (String) -> Unit,
    showPassword: Boolean,
    onVisibilityChange: () -> Unit
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        placeholder = { Text(text = "비밀번호", color = Color.Gray) },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.DarkGray,
            unfocusedContainerColor = Color.DarkGray,
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        shape = RoundedCornerShape(5.dp),
        visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            Text(
                text = if (showPassword) "Hide" else "Show",
                color = Color.White,
                modifier = Modifier.clickable { onVisibilityChange() }
            )
        },
        singleLine = true
    )
}

@Composable
fun SignInButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Blue,
            contentColor = Color.White
        )
    ) {
        Text(text = text, fontSize = 15.sp)
    }
}

@Composable
fun ActionLinks(onSignUpClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        ActionText(text = "아이디 찾기", onClick = { })
        Text(text = "|", color = Color.Gray)
        ActionText(text = "비밀번호 재설정", onClick = { })
        Text(text = "|", color = Color.Gray)
        ActionText(text = "회원 가입", onClick = onSignUpClick)
    }
}

@Composable
fun ActionText(text: String, onClick: () -> Unit) {
    Text(
        text = text,
        color = Color.Gray,
        modifier = Modifier.clickable(onClick = onClick)
    )
}

@Composable
fun SNSButtons() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        val snsColors = listOf(Color.Yellow, Color.Blue, Color.Green, Color(0xFF113065), Color.White)
        snsColors.forEach { color ->
            Button(
                onClick = {},
                shape = CircleShape,
                modifier = Modifier
                    .padding(5.dp)
                    .size(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = color)
            ) { }
        }
    }
}

fun showSnackbar(context: ComponentActivity, message: String) {
    // Snackbar 메시지 표시를 위한 메서드
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

@Preview(showBackground = true)
@Composable
fun SignInScreenPreview() {
    SignInScreen(onSignUpClick = {}, onSignInAttempt = { _, _ -> })
}