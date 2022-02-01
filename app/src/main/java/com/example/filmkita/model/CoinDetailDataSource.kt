package com.example.filmkita.model

import com.example.filmkita.data.OperationCallback
import com.example.filmkita.data.OperationCallbackDetail
import com.example.filmkita.utils.WrappedResponseDetail

interface CoinDetailDataSource {

    fun getCoinDetail(callback: OperationCallbackDetail<CoinDetail>, id:String)
    fun cancel()
}