package com.example.filmkita.model.repository

import com.example.filmkita.data.OperationCallback
import com.example.filmkita.model.Coin
import com.example.filmkita.model.datasource.CoinDataSource

class CoinRepository(private val coinDataSource: CoinDataSource) {
    fun getCoins(callback:OperationCallback<Coin>) {
        coinDataSource.getCoins(callback)
    }

    fun cancel () {
        coinDataSource.cancel()
    }
}