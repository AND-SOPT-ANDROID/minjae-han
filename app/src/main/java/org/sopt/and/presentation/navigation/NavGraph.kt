package org.sopt.and.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import org.sopt.and.Route
import org.sopt.and.presentation.home.HomeScreen
import org.sopt.and.presentation.mypage.MyPageScreen
import org.sopt.and.presentation.search.SearchScreen
import org.sopt.and.presentation.signin.SignInScreen
import org.sopt.and.presentation.signup.SignUpScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String = Route.SignIn.route,
    isLoggedIn: (Boolean) -> Unit = {}
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(route = Route.Home.route) {
            HomeScreen()
        }

        composable(route = Route.SignIn.route) {
            SignInScreen(
                onLoginSuccess = {
                    navController.navigate(Route.Home.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                    isLoggedIn(true)
                },
                onSignUpClick = {
                    navController.navigate(Route.SignUp.route)
                }
            )
        }

        composable(route = Route.SignUp.route) {
            SignUpScreen(
                onSignUpSuccess = {
                    navController.navigate(Route.SignIn.route) {
                        popUpTo(Route.SignUp.route) {
                            inclusive = true
                        }
                    }
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(route = Route.Search.route) {
            SearchScreen()
        }

        composable(route = Route.MyPage.route) {
            MyPageScreen()
        }
    }
}