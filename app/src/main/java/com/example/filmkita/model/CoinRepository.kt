package com.example.filmkita.model

import com.example.filmkita.data.OperationCallback

class CoinRepository(private val coinDataSource: CoinDataSource) {
    fun getCoins(callback:OperationCallback<Coin>) {
        coinDataSource.getCoins(callback)
    }

    fun cancel () {
        coinDataSource.cancel()
    }
}