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
        //상단바
        Row (modifier = Modifier
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier
                    .weight(1f)
                    .clickable { },
                text = "<",
                fontSize = 40.sp,
                color = Color.White,
            )
            Text( modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically),
                fontWeight = FontWeight.Bold,
                text = "Wavve",
                textAlign = TextAlign.Center,
                color = Color.White,
                fontSize = 30.sp,
            )
            Spacer(modifier = Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(30.dp))

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
                    text = "이메일 주소 또는 아이디",
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

        Spacer(modifier = Modifier.height(5.dp))

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
                    text = "비밀번호",
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

        Spacer(modifier = Modifier.height(30.dp))

        //로그인 버튼
        Button(
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Blue,   // 버튼의 배경색
                contentColor = Color.White     // 버튼 내부 텍스트 색상
            )
        ) {
            Text(
                text = "로그인",
                fontSize = 15.sp
            )
        }

        Spacer(modifier = Modifier.height(30.dp))

        //아이디 찾기,비번 재설정,회원가입 버튼
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Spacer(modifier=Modifier)
            Text(
                modifier = Modifier.clickable {  },
                text = "아이디 찾기",
                color = Color.Gray
            )
            Text(
                modifier = Modifier,
                text = "|",
                color = Color.Gray
            )
            Text(
                modifier = Modifier.clickable {  },
                text = "비밀번호 재설정",
                color = Color.Gray
            )
            Text(
                modifier = Modifier,
                text = "|",
                color = Color.Gray
            )
            Text(
                modifier = Modifier.clickable {  },
                text = "회원 가입",
                color = Color.Gray
            )
            Spacer(modifier=Modifier)
        }

        Spacer(modifier = Modifier.height(60.dp))
        Text(
            text = "또는 다른 서비스 계정으로 가입",
            color = Color.White,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        //SNS 버튼
        Spacer(modifier = Modifier.height(20.dp))
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

        Spacer(modifier = Modifier.height(15.dp))

        //주의사항 텍스트
        Text(
            text = "• SNS계정으로 간편하게 가입하여 서비스를 이용하실 수 있습니다. 기존 POOQ 계정 또는 Wavve 계정과는 연동되지 않으니 이용에 참고하세요.",
            color = Color.Gray,
        )


    }

}

@Preview(showBackground = true)
@Composable
fun SignInScreenPreview(){
    SignInScreen()
}