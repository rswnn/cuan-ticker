package com.example.filmkita.model

import com.example.filmkita.data.OperationCallback

interface CoinDataSource {

    fun getCoins (callback: OperationCallback<Coin>)

    fun cancel()

}