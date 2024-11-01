package org.sopt.and

sealed class Route(val route: String) {
    data object Home : Route("home")

    data class SignIn(val email: String = "", val password: String = "") :
        Route("signIn?email={email}&password={password}") {
        fun createRoute(email: String = "", password: String = "") =
            "signIn?email=$email&password=$password"
    }

    data object SignUp : Route("signUp")
    data object Search : Route("search")

    data class MyPage(val email: String) : Route("myPage?email={email}") {
        fun createRoute(email: String) = "myPage?email=$email"
    }
}