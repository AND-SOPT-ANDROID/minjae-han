//bottomNavBar.kt
package org.sopt.and

import android.content.Intent
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavBar(navController: NavController) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Search,
        BottomNavItem.My
    )

    val context = LocalContext.current

    BottomNavigation(
        backgroundColor = Color.Black,
        contentColor = Color.White,
        modifier = Modifier.fillMaxWidth()
    ) {
        val navBackStackEntry = navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry.value?.destination?.route

        items.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) },
                selected = currentRoute == item.route,
                selectedContentColor = Color.White,
                unselectedContentColor = Color.Gray,
                onClick = {
                    when (item.route) {
                        BottomNavItem.My.route -> {
                            // MyActivity로 이동
                            val intent = Intent(context, MyActivity::class.java)
                            context.startActivity(intent)
                        }
                        else -> {
                            if (currentRoute != item.route) {
                                navController.navigate(item.route)
                            }
                        }
                    }
                },
                alwaysShowLabel = true
            )
        }
    }
}


