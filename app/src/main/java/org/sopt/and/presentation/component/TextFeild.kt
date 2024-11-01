package org.sopt.and.presentation.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun EmailInputField(
    value: String,
    placeholder: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    errorMessage: String? = null
) {
    val containerColor by animateColorAsState(
        targetValue = if (isError) Color.DarkGray.copy(red = 0.4f) else Color.DarkGray,
        label = "containerColor"
    )

    Column {
        TextField(
            value = value,
            onValueChange = onValueChange,
            modifier = modifier
                .fillMaxWidth()
                .height(60.dp),
            placeholder = {
                Text(
                    text = placeholder,
                    color = Color.Gray
                )
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = containerColor,
                unfocusedContainerColor = containerColor,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedIndicatorColor = if (isError) Color.Red.copy(alpha = 0.5f) else Color.Transparent,
                unfocusedIndicatorColor = if (isError) Color.Red.copy(alpha = 0.5f) else Color.Transparent,
                errorContainerColor = containerColor,
                errorIndicatorColor = Color.Red.copy(alpha = 0.5f)
            ),
            shape = RoundedCornerShape(5.dp),
            singleLine = true,
            isError = isError
        )

        if (isError && errorMessage != null) {
            Text(
                text = errorMessage,
                color = Color.Red.copy(alpha = 0.8f),
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 4.dp, top = 4.dp)
            )
        }
    }
}

@Composable
fun PasswordInputField(
    value: String,
    onValueChange: (String) -> Unit,
    showPassword: Boolean,
    placeholder: String,
    onVisibilityChange: () -> Unit,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    errorMessage: String? = null
) {
    val containerColor by animateColorAsState(
        targetValue = if (isError) Color.DarkGray.copy(red = 0.4f) else Color.DarkGray,
        label = "containerColor"
    )

    Column {
        TextField(
            value = value,
            onValueChange = onValueChange,
            modifier = modifier
                .fillMaxWidth()
                .height(60.dp),
            placeholder = {
                Text(
                    text = placeholder,
                    color = Color.Gray
                )
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = containerColor,
                unfocusedContainerColor = containerColor,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedIndicatorColor = if (isError) Color.Red.copy(alpha = 0.5f) else Color.Transparent,
                unfocusedIndicatorColor = if (isError) Color.Red.copy(alpha = 0.5f) else Color.Transparent,
                errorContainerColor = containerColor,
                errorIndicatorColor = Color.Red.copy(alpha = 0.5f)
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
            singleLine = true,
            isError = isError
        )

        if (isError && errorMessage != null) {
            Text(
                text = errorMessage,
                color = Color.Red.copy(alpha = 0.8f),
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 4.dp, top = 4.dp)
            )
        }
    }
}