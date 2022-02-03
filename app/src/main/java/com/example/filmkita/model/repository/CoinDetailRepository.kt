package com.example.filmkita.model.repository

import com.example.filmkita.data.OperationCallbackDetail
import com.example.filmkita.model.CoinDetail
import com.example.filmkita.model.datasource.CoinDetailDataSource

class CoinDetailRepository(private val coinDetailDataSource: CoinDetailDataSource) {
    fun getCoinDetail(callback: OperationCallbackDetail<CoinDetail>, id:String) {
        coinDetailDataSource.getCoinDetail(callback, id)
    }

    fun cancel () {
        coinDetailDataSource.cancel()
    }
}