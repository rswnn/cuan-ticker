package com.example.filmkita.model.datasource

import com.example.filmkita.data.OperationCallbackDetail
import com.example.filmkita.model.CoinDetail

interface CoinDetailDataSource {

    fun getCoinDetail(callback: OperationCallbackDetail<CoinDetail>, id:String)
    fun cancel()
}