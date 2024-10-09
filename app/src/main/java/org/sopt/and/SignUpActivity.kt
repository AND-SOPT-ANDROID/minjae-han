package org.sopt.and

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.sopt.and.ui.theme.ANDANDROIDTheme

class SignUpActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ANDANDROIDTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    SignUpScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun SignUpScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black)
            .padding(20.dp)
    ) {
        //상단라인
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier)

            Text(
                text = "회원가입",
                color = Color.White,
                fontSize = 20.sp,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
            Text(
                color = Color.White,
                text = "X",
                fontSize = 30.sp,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .clickable { }
            )
        }

        Spacer(modifier = Modifier.height(40.dp))
        //안내 텍스트 문구
        Text(
            modifier = Modifier,
            text = "이메일과 비밀번호 만으로\nWavve를 즐길 수 있어요!",
            fontSize = 23.sp,
            color = Color.White,
        )

        Spacer(modifier = Modifier.height(40.dp))
        //아이디 입력 필드
        var emailText by remember { mutableStateOf("") }
        TextField(
            value = emailText,
            onValueChange = { emailText = it },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            placeholder = {
                Text(
                text = "wavve@example.com",
                color = Color.Gray
                ) },
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

        Spacer(modifier = Modifier.height(10.dp))
        //아이디 주의사항 텍스트
        Text(
            text = "! 로그인, 비밀번호 찾기, 알림에 사용되니 정확한 이메일을\n입력해 주세요.",
            color = Color.White,
        )

        Spacer(modifier = Modifier.height(20.dp))

        // 비밀번호 입력 필드
        var passWordText by remember { mutableStateOf("") }
        var showPassword by remember { mutableStateOf(false) } // "Show"와 "Hide"를 위한 상태 저장

        TextField(
            value = passWordText,
            onValueChange = { passWordText = it },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            placeholder = {
                Text(
                    text = "wavve 비밀번호 설정",
                    color = Color.Gray
                )
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.DarkGray,
                unfocusedContainerColor = Color.DarkGray,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(5.dp),
            visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(), // 비밀번호 보임 여부 결정
            trailingIcon = {
                Text(
                    text = if (showPassword) "Hide" else "Show", // 상태에 따라 텍스트 변경
                    color = Color.White,
                    modifier = Modifier.clickable {
                        showPassword = !showPassword // 클릭 시 상태 변경
                    }
                )
            },
            singleLine = true
        )

        Spacer(modifier = Modifier.height(10.dp))
        //아이디 주의사항 텍스트
        Text(
            text = "! 비밀번호는 8-20자 이내, 영문 대소문자, 숫자, 특수문자 중\n 3가지 이상 혼용하여 입력해 주세요.",
            color = Color.White,
        )

        Spacer(modifier = Modifier.height(60.dp))
        Text(
            text = "또는 다른 서비스 계정으로 가입",
            color = Color.White,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(30.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = {},
                shape = CircleShape,
                modifier = Modifier
                    .padding(5.dp)
                    .size(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Yellow)
            ) { }
            Button(
                onClick = {},
                shape = CircleShape,
                modifier = Modifier
                    .padding(5.dp)
                    .size(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)
            ) { }
            Button(
                onClick = {},
                shape = CircleShape,
                modifier = Modifier
                    .padding(5.dp)
                    .size(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Green)
            ) { }
            Button(
                onClick = {},
                shape = CircleShape,
                modifier = Modifier
                    .padding(5.dp)
                    .size(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF113065))
            ) { }
            Button(
                onClick = {},
                shape = CircleShape,
                modifier = Modifier
                    .padding(5.dp)
                    .size(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White)
            ) { }
        }

        Spacer(modifier = Modifier.height(30.dp))

        //주의사항 텍스트
        Text(
            text = "• SNS계정으로 간편하게 가입하여 서비스를 이용하실 수 있습니다. 기존 POOQ 계정 또는 Wavve 계정과는 연동되지 않으니 이용에 참고하세요.",
            color = Color.Gray,
        )





    }
}

@Preview
@Composable
private fun SignUpScreenPreview() {
    SignUpScreen()

}
