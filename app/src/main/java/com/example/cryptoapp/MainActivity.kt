package com.example.cryptoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.cryptoapp.core.network.NetworkFactory
import com.example.cryptoapp.data.MarketRepositoryImpl
import com.example.cryptoapp.feature.market.MarketViewModel

class MainActivity : ComponentActivity() {

    private val marketViewModel: MarketViewModel by lazy {
        val service = NetworkFactory.createCryptoApiService(
            baseUrl = "https://api.example.com/"
        )
        val repository = MarketRepositoryImpl(service)
        MarketViewModel(repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 这里就是“调接口”的起点：load() -> Repository -> CryptoApiService.getRealtimePrices()
        marketViewModel.load(listOf("BTC", "ETH", "SOL"))

        setContent {
            CryptoRoot(marketViewModel)
        }
    }
}

@Composable
private fun CryptoRoot(viewModel: MarketViewModel) {
    val state by viewModel.uiState.collectAsState()
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        val text = when {
            state.isLoading -> "行情加载中..."
            state.error != null -> "加载失败: ${state.error}"
            else -> "已加载 ${state.prices.size} 条行情"
        }
        Text(text)
    }
}
