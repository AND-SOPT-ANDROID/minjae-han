package org.sopt.and.presentation.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


// SNS 버튼 컴포넌트
@Composable
fun SNSButton(onClick : ()  -> Unit, color: Color) {
    Button(
        onClick = onClick,
        shape = CircleShape,
        modifier = Modifier
            .padding(5.dp)
            .size(50.dp),
        colors = ButtonDefaults.buttonColors(containerColor = color)
    ) { }
}

@Composable
fun SignBottomBox(){
    Column {
        Text(
            text = "또는 다른 서비스 계정으로 가입",
            color = Color.Gray,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(30.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            listOf(Color.Yellow, Color.Blue, Color.Green, Color(0xFF113065), Color.White)
                .forEach { color ->
                    SNSButton(onClick = {}, color = color)
                }
        }

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = "• SNS계정으로 간편하게 가입하여 서비스를 이용하실 수 있습니다. 기존 POOQ 계정 또는 Wavve 계정과는 연동되지 않으니 이용에 참고하세요.",
            color = Color.Gray,
        )
    }

}

@Preview
@Composable
private fun SignBottomBoxView() {
    SignBottomBox()

}
