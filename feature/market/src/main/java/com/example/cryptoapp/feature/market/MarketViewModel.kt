package com.example.cryptoapp.feature.market

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptoapp.core.model.CoinPrice
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MarketViewModel(
    private val marketRepository: MarketRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(MarketUiState())
    val uiState: StateFlow<MarketUiState> = _uiState.asStateFlow()

    fun load(symbols: List<String>) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            runCatching {
                marketRepository.getRealtimePrices(symbols)
            }.onSuccess { prices ->
                _uiState.value = MarketUiState(isLoading = false, prices = prices)
            }.onFailure { throwable ->
                _uiState.value = MarketUiState(
                    isLoading = false,
                    error = throwable.message ?: "Unknown error"
                )
            }
        }
    }
}

data class MarketUiState(
    val isLoading: Boolean = false,
    val prices: List<CoinPrice> = emptyList(),
    val error: String? = null
)

fun interface MarketRepository {
    suspend fun getRealtimePrices(symbols: List<String>): List<CoinPrice>
}
