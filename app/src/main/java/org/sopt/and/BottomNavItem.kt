package org.sopt.and

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(val label: String, val icon: ImageVector, val route: String) {
    object Home : BottomNavItem("홈", Icons.Default.Home, "home")
    object Search : BottomNavItem("검색", Icons.Default.Search, "search")
    object My : BottomNavItem("MY", Icons.Default.Person, "my")
}
