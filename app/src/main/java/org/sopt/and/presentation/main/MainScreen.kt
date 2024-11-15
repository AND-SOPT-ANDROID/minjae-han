package org.sopt.and.presentation.main

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
    var isLoggedIn by remember { mutableStateOf(false) }

    Scaffold(
        bottomBar = {
            if (isLoggedIn) {
                BottomNavigationBar(
                    navController = navController
                )
            }
        }
    ) { innerPadding ->
        NavGraph(
            navController = navController,
            isLoggedIn = { loggedIn ->
                isLoggedIn = loggedIn
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