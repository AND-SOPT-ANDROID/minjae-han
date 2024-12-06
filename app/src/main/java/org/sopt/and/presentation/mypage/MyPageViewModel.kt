package org.sopt.and.presentation.mypage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.sopt.and.data.ServicePool
import org.sopt.and.data.local.AuthLocalDataSource
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

class MyPageViewModel(
    private val authLocalDataSource: AuthLocalDataSource
) : ViewModel() {
    private val _uiState = MutableStateFlow(MyPageUiState())
    val uiState: StateFlow<MyPageUiState> = _uiState.asStateFlow()

    data class MyPageUiState(
        val hobby: String = "",
        val isLoading: Boolean = false,
        val errorMessage: String? = null
    )

    init {
        fetchMyHobby()
    }

    private fun fetchMyHobby() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                authLocalDataSource.getToken().collect { token ->
                    if (token != null) {
                        try {
                            val response = ServicePool.authService.getMyHobby(token)
                            when {
                                response.isSuccessful && response.body()?.result != null -> {
                                    _uiState.update {
                                        it.copy(hobby = response.body()?.result?.hobby ?: "")
                                    }
                                }
                                response.code() == 401 -> {
                                    _uiState.update {
                                        it.copy(errorMessage = "토큰이 없습니다")
                                    }
                                }
                                response.code() == 403 -> {
                                    _uiState.update {
                                        it.copy(errorMessage = "유효하지 않은 토큰입니다")
                                    }
                                }
                                else -> {
                                    _uiState.update {
                                        it.copy(errorMessage = "취미 조회에 실패했습니다")
                                    }
                                }
                            }
                        } catch (e: HttpException) {
                            _uiState.update {
                                it.copy(errorMessage = "서버 응답 오류: ${e.code()}")
                            }
                        } catch (e: SocketTimeoutException) {
                            _uiState.update {
                                it.copy(errorMessage = "서버 응답 시간 초과")
                            }
                        } catch (e: IOException) {
                            _uiState.update {
                                it.copy(errorMessage = "네트워크 연결 오류")
                            }
                        } catch (e: Exception) {
                            _uiState.update {
                                it.copy(errorMessage = "알 수 없는 오류가 발생했습니다")
                            }
                        } finally {
                            _uiState.update { it.copy(isLoading = false) }
                        }
                    } else {
                        _uiState.update {
                            it.copy(
                                errorMessage = "로그인이 필요합니다",
                                isLoading = false
                            )
                        }
                    }
                }
            } catch (e: IOException) {
                _uiState.update {
                    it.copy(errorMessage = "토큰 저장소 접근 오류")
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(errorMessage = "토큰 조회 중 알 수 없는 오류 발생")
                }
            } finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    companion object {
        fun provideFactory(
            authLocalDataSource: AuthLocalDataSource
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return MyPageViewModel(authLocalDataSource) as T
            }
        }
    }
}
