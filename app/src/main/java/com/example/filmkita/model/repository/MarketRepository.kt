package com.example.filmkita.model.repository

import com.example.filmkita.data.OperationCallbackDetail
import com.example.filmkita.model.Market
import com.example.filmkita.model.datasource.MarketDataSource

class MarketRepository(private val marketDataSource: MarketDataSource) {
    fun getMarket(callback:OperationCallbackDetail<Market>, id:String) {
        marketDataSource.getMarket(callback, id)
    }

    fun cancel() {
        marketDataSource.cancel()
    }
}