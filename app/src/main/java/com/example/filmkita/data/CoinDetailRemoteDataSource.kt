package com.example.filmkita.data

import com.example.filmkita.model.CoinDetail
import com.example.filmkita.model.CoinDetailDataSource
import com.example.filmkita.utils.WrappedResponseDetail
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CoinDetailRemoteDataSource(apiClient:ApiClient): CoinDetailDataSource {
    private var call: Call<WrappedResponseDetail<CoinDetail>>? = null
    private var service = apiClient.build()
    override fun getCoinDetail(callback: OperationCallbackDetail<CoinDetail>, id: String) {
        call = service?.getCoinDetail(id)
        call?.enqueue(object : Callback<WrappedResponseDetail<CoinDetail>> {
            override fun onResponse(
                call: Call<WrappedResponseDetail<CoinDetail>>,
                response: Response<WrappedResponseDetail<CoinDetail>>
            ) {
                response.body()?.let {
                    if (response.isSuccessful) {
                        callback.onSuccess(it?.data)
                    } else {
                        println("Kena 2")
                        callback.onError(it.toString())
                    }
                }
            }

            override fun onFailure(call: Call<WrappedResponseDetail<CoinDetail>>, t: Throwable) {
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