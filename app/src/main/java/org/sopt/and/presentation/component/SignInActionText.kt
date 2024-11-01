package org.sopt.and.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun ActionText(text: String, nextScreen: () -> Unit) {
    Text(
        text = text,
        color = Color.Gray,
        modifier = Modifier.clickable { nextScreen() }
    )
}

