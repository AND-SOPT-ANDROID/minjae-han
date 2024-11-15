package org.sopt.and.presentation.main

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import org.sopt.and.presentation.navigation.BottomNavigationBar
import org.sopt.and.presentation.navigation.NavGraph

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    var bottomNaviVisible by remember { mutableStateOf(false) }
    var userEmail by remember { mutableStateOf("") }

    Scaffold(
        bottomBar = {
            if (bottomNaviVisible) {
                BottomNavigationBar(
                    navController = navController,
                    userEmail = userEmail
                )
            }
        }
    ) { innerPadding: PaddingValues ->
        NavGraph(
            navController = navController,
            isLogined = { isLogined ->
                bottomNaviVisible = isLogined
            },
            onEmailUpdated = { email ->
                userEmail = email
            },
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Preview
@Composable
private fun MainScreenPreview() {
    MainScreen()

}