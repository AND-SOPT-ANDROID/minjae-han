package org.sopt.and.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import org.sopt.and.Route

sealed class BottomNavItem(
    val name: String,
    val icon: ImageVector,
    val route: String
) {
    data object Home : BottomNavItem(
        name = "홈",
        icon = Icons.Default.Home,
        route = Route.Home.route
    )

    data object Search : BottomNavItem(
        name = "검색",
        icon = Icons.Default.Search,
        route = Route.Search.route
    )

    data object MyPage : BottomNavItem(
        name = "MY",
        icon = Icons.Default.Person,
        route = Route.MyPage.route
    )

    companion object {
        val items = listOf(Home, Search, MyPage)
    }
}