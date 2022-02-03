package com.example.filmkita.data

import com.example.filmkita.model.Coin
import com.example.filmkita.model.CoinDetail
import com.example.filmkita.model.Market
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiServices {

    @GET("coins/markets?vs_currency=usd&order=market_cap_desc&per_page=100&page=1&sparkline=false")
    fun coins(): Call<List<Coin>>

    @GET("coins/{id}?localization=false&tickers=false&market_data=false&community_data=false&developer_data=false&sparkline=false")
    fun getCoinDetail(@Path("id")id:String): Call<CoinDetail>

    @GET("coins/{id}/market_chart?vs_currency=usd&days=90&interval=daily")
    fun getMarket(@Path("id")id:String):Call<Market>
}