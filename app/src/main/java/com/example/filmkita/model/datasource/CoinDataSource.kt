package com.example.filmkita.model.datasource

import com.example.filmkita.data.OperationCallback
import com.example.filmkita.model.Coin

interface CoinDataSource {

    fun getCoins (callback: OperationCallback<Coin>)

    fun cancel()

}