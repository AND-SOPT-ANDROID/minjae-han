package org.sopt.and.domain.error

sealed class AuthError : Exception() {
    object InvalidCredentials : AuthError()
    object WrongPassword : AuthError()
    object Unauthorized : AuthError()
    object Forbidden : AuthError()
    object NetworkError : AuthError()
    object Unknown : AuthError()

    override val message: String
        get() = when(this) {
            is InvalidCredentials -> "아이디 또는 비밀번호를 확인해주세요"
            is WrongPassword -> "비밀번호가 일치하지 않습니다"
            is Unauthorized -> "로그인이 필요합니다"
            is Forbidden -> "인증이 만료되었습니다"
            is NetworkError -> "네트워크 오류가 발생했습니다"
            is Unknown -> "알 수 없는 오류가 발생했습니다"
        }
}
