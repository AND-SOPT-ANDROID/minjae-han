package org.sopt.and.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
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
    startDestination: String = Route.SignIn().route,
    isLogined: (Boolean) -> Unit = {},
    onEmailUpdated: (String) -> Unit = {}
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(route = Route.Home.route) {
            HomeScreen()
        }

        composable(
            route = Route.SignIn().route,
            arguments = listOf(
                navArgument("email") {
                    type = NavType.StringType
                    defaultValue = ""
                },
                navArgument("password") {
                    type = NavType.StringType
                    defaultValue = ""
                }
            )
        ) { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""
            val password = backStackEntry.arguments?.getString("password") ?: ""
            SignInScreen(
                email = email,
                password = password,
                navigateToMyPage = { userEmail ->
                    onEmailUpdated(userEmail)
                    navController.navigate(Route.Home.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                    isLogined(true)
                },
                navigateToSignUp = {
                    navController.navigate(Route.SignUp.route) {
                        popUpTo(Route.SignIn().route) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }

        composable(route = Route.SignUp.route) {
            SignUpScreen(
                navigateToSignIn = { user ->
                    navController.navigate(Route.SignIn(user.email, user.password).createRoute(user.email, user.password)) {
                        popUpTo(Route.SignUp.route) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(route = Route.Search.route) {
            SearchScreen()
        }

        composable(
            route = Route.MyPage("").route,
            arguments = listOf(
                navArgument("email") {
                    type = NavType.StringType
                    defaultValue = ""
                }
            )
        ) { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""
            MyPageScreen(email = email)
        }
    }
}