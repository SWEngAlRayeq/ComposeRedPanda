package app.red_panda.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.red_panda.domain.model.RedPanda
import app.red_panda.domain.usecase.GetRedPandaFactUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class RedPandaUiState(
    val redPanda: RedPanda? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)


@HiltViewModel
class RedPandaViewModel @Inject constructor(
    private val getRedPandaFactUseCase: GetRedPandaFactUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(RedPandaUiState(isLoading = true))
    val state = _state

    init {
        loadRedPanda()
    }

    fun loadRedPanda() {
        _state.value = RedPandaUiState(isLoading = true)
        viewModelScope.launch {
            val response = getRedPandaFactUseCase()
            if (response.isSuccess) {
                _state.value = RedPandaUiState(redPanda = response.getOrNull())
            } else {
                _state.value = RedPandaUiState(error = response.exceptionOrNull()?.message)
            }
        }
    }

}