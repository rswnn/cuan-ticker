package com.example.filmkita.model.datasource

import com.example.filmkita.data.OperationCallbackDetail
import com.example.filmkita.model.Market

interface MarketDataSource {

    fun getMarket(callback: OperationCallbackDetail<Market>, id:String)

    fun cancel()

}