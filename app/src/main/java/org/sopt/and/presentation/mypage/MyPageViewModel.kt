package org.sopt.and.presentation.mypage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.sopt.and.domain.usecase.auth.GetMyHobbyUseCase
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val getMyHobbyUseCase: GetMyHobbyUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(MyPageState())
    val state = _state.asStateFlow()

    private val _effect = Channel<MyPageEffect>()
    val effect = _effect.receiveAsFlow()

    init {
        processIntent(MyPageIntent.LoadMyHobby)
    }

    fun processIntent(intent: MyPageIntent) {
        when (intent) {
            MyPageIntent.LoadMyHobby -> fetchMyHobby()
            MyPageIntent.RefreshData -> fetchMyHobby()
            MyPageIntent.OnPurchaseClick -> handlePurchaseClick()
        }
    }

    private fun fetchMyHobby() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            getMyHobbyUseCase()
                .onSuccess { hobby ->
                    _state.update { it.copy(hobby = hobby) }
                }
                .onFailure { exception ->
                    _effect.send(MyPageEffect.ShowError(exception.message ?: "Unknown error"))
                }

            _state.update { it.copy(isLoading = false) }
        }
    }

    private fun handlePurchaseClick() {
        viewModelScope.launch {
            _effect.send(MyPageEffect.NavigateToPurchase)
        }
    }

}
