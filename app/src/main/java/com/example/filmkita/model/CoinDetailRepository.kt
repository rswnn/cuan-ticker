package com.example.filmkita.model

import com.example.filmkita.data.OperationCallbackDetail

class CoinDetailRepository(private val coinDetailDataSource: CoinDetailDataSource) {
    fun getCoinDetail(callback: OperationCallbackDetail<CoinDetail>, id:String) {
        coinDetailDataSource.getCoinDetail(callback, id)
    }

    fun cancel () {
        coinDetailDataSource.cancel()
    }
}