package com.example.filmkita.data

import com.example.filmkita.model.Coin
import com.example.filmkita.model.datasource.CoinDataSource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CoinRemoteDataSource(apiClient: ApiClient): CoinDataSource {
    private var call: Call<List<Coin>>? = null
    private var service = apiClient.build()
    override fun getCoins(callback: OperationCallback<Coin>) {
        call = service?.coins()
        call?.enqueue(object : Callback<List<Coin>> {
            override fun onResponse(
                call: Call<List<Coin>>,
                response: Response<List<Coin>>
            ) {
                response.body()?.let {
                    if (response.isSuccessful) {
                        callback.onSuccess(it)
                    } else {
                        callback.onError(it.toString())
                    }
                }
            }

            override fun onFailure(call: Call<List<Coin>>, t: Throwable) {
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
