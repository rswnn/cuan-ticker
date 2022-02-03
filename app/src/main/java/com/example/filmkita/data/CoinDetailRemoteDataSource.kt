package com.example.filmkita.data

import com.example.filmkita.model.CoinDetail
import com.example.filmkita.model.datasource.CoinDetailDataSource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CoinDetailRemoteDataSource(apiClient:ApiClient): CoinDetailDataSource {
    private var call: Call<CoinDetail>? = null
    private var service = apiClient.build()
    override fun getCoinDetail(callback: OperationCallbackDetail<CoinDetail>, id: String) {
        call = service?.getCoinDetail(id)
        call?.enqueue(object : Callback<CoinDetail> {
            override fun onResponse(call: Call<CoinDetail>, response: Response<CoinDetail>) {
                response.body()?.let {
                    if (response.isSuccessful) {
                        callback.onSuccess(it)
                    } else {
                        callback.onError(it.toString())
                    }
                }
            }

            override fun onFailure(call: Call<CoinDetail>, t: Throwable) {
                callback.onError(t.toString())
            }

        })
    }

    override fun cancel() {
        call?.let {
            it.cancel()
        }
    }
}