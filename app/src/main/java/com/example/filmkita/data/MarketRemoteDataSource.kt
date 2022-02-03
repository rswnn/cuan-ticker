package com.example.filmkita.data

import com.example.filmkita.model.Market
import com.example.filmkita.model.datasource.MarketDataSource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MarketRemoteDataSource(apiClient:ApiClient): MarketDataSource {

    private var call: Call<Market>? = null
    private var services = apiClient.build()

    override fun getMarket(callback: OperationCallbackDetail<Market>, id:String) {
        call = services?.getMarket(id)
        call?.enqueue(object: Callback<Market> {
            override fun onResponse(call: Call<Market>, response: Response<Market>) {
                response.body()?.let {
                    if(response.isSuccessful) {
                        callback.onSuccess(it)
                    } else {
                        callback.onError(it.toString())
                    }
                }
            }

            override fun onFailure(call: Call<Market>, t: Throwable) {
                callback.onError(t.message)
            }

        })
    }

    override fun cancel() {
        call?.let {
            it.cancel()
        }
    }
}