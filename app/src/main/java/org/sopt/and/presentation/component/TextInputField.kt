// presentation/component/TextInputField.kt
package org.sopt.and.presentation.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun TextInputField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    isError: Boolean = false,
    maxLength: Int = 8,
    singleLine: Boolean = true
) {
    TextField(
        value = value,
        onValueChange = {
            if (it.length <= maxLength) {
                onValueChange(it)
            }
        },
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
            focusedContainerColor = Color.DarkGray,
            unfocusedContainerColor = Color.DarkGray,
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
            focusedIndicatorColor = if (isError) Color.Red.copy(alpha = 0.5f) else Color.Transparent,
            unfocusedIndicatorColor = if (isError) Color.Red.copy(alpha = 0.5f) else Color.Transparent,
            errorContainerColor = Color.DarkGray,
            errorIndicatorColor = Color.Red.copy(alpha = 0.5f)
        ),
        shape = RoundedCornerShape(5.dp),
        singleLine = singleLine,
        isError = isError
    )
}