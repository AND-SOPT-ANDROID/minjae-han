package org.sopt.and

import android.os.Bundle
import android.widget.Space
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.ColorRes
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.sopt.and.ui.theme.ANDANDROIDTheme

class SignInActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ANDANDROIDTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                SignInScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun SignInScreen(modifier: Modifier = Modifier) {
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
            value = remember { mutableStateOf("") },
            placeholder = "이메일 주소 또는 아이디"
        )

        Spacer(modifier = Modifier.height(5.dp))

        // 비밀번호 입력 필드
        PasswordField()

        Spacer(modifier = Modifier.height(30.dp))

        // 로그인 버튼
        SignInButton(text = "로그인", onClick = {  })

        Spacer(modifier = Modifier.height(30.dp))

        // 아이디 찾기, 비번 재설정, 회원가입 버튼
        ActionLinks()

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

// 상단바 컴포넌트화
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

// 아이디 및 비밀번호 입력 필드 컴포넌트화
@Composable
fun InputField(value: MutableState<String>, placeholder: String) {
    TextField(
        value = value.value,
        onValueChange = { value.value = it },
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

// 비밀번호 입력 필드 컴포넌트화
@Composable
fun PasswordField() {
    var passWordText by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }

    TextField(
        value = passWordText,
        onValueChange = { passWordText = it },
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
                modifier = Modifier.clickable { showPassword = !showPassword }
            )
        },
        singleLine = true
    )
}

// 로그인 버튼
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

// 아이디 찾기, 비번 재설정, 회원가입 컴포넌트화
@Composable
fun ActionLinks() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        ActionText(text = "아이디 찾기", onClick = { })
        Text(text = "|", color = Color.Gray)
        ActionText(text = "비밀번호 재설정", onClick = {  })
        Text(text = "|", color = Color.Gray)
        ActionText(text = "회원 가입", onClick = { })
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

// SNS 버튼들을 컴포넌트화
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

@Preview(showBackground = true)
@Composable
fun SignInScreenPreview(){
    SignInScreen()
}