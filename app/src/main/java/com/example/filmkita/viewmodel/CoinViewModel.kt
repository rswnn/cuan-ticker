package com.example.filmkita.viewmodel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.filmkita.data.OperationCallback
import com.example.filmkita.data.OperationCallbackDetail
import com.example.filmkita.model.Coin
import com.example.filmkita.model.CoinDetail
import com.example.filmkita.model.Market
import com.example.filmkita.model.MarketChart
import com.example.filmkita.model.repository.CoinDetailRepository
import com.example.filmkita.model.repository.CoinRepository
import com.example.filmkita.model.repository.MarketRepository
import java.time.LocalDateTime
import java.util.*

class CoinViewModel(private val coinRepository: CoinRepository?, private val coinDetailRepository: CoinDetailRepository?, private val marketRepository: MarketRepository?): ViewModel() {
    private val _coins = MutableLiveData<List<Coin>>().apply { value = emptyList() }
    val coins: LiveData<List<Coin>> = _coins

    private val _coinDetail = MutableLiveData<CoinDetail>().apply { value = null }
    val coinDetail: LiveData<CoinDetail> = _coinDetail

    private val _market = MutableLiveData<Market>().apply { value = null }
    val market: LiveData<Market> = _market

    private val _marketChart = MutableLiveData<List<MarketChart>>().apply { value = emptyList() }
    val marketChart: LiveData<List<MarketChart>> = _marketChart

    private val _isViewLoading = MutableLiveData<Boolean>()
    val isViewLoading: LiveData<Boolean> = _isViewLoading

    private val _onMessageError = MutableLiveData<Any>()
    val onMessageError: LiveData<Any> = _onMessageError

    private val _isEmptyList = MutableLiveData<Boolean>()
    val isEmptyList: LiveData<Boolean> = _isEmptyList


    fun loadCoins() {
        _isViewLoading.value = true
        coinRepository?.getCoins(object : OperationCallback<Coin> {
            override fun onSuccess(data: List<Coin>?) {
                _isViewLoading.value = false
                if(data.isNullOrEmpty()) {
                    _isEmptyList.value = true
                } else {
                    _coins.value = data
                }
            }

            override fun onError(error: String?) {
                _isViewLoading.value = false
                _onMessageError.value = error
            }

        })
    }

    fun loadCoinDetail(id:String) {
        _isViewLoading.value = true
        coinDetailRepository?.getCoinDetail(object:OperationCallbackDetail<CoinDetail> {
            override fun onSuccess(data: CoinDetail?) {
                _isViewLoading.value = false
                if (data == null) {
                    _isEmptyList.value = true // should handle obj if null
                } else {
                    _coinDetail.value = data
                }
            }

            override fun onError(error: String?) {
                _isViewLoading.value = false
                _onMessageError.value = error
            }

        }, id)
    }

    fun loadMarket(id: String) {
        _isViewLoading.value = true
        marketRepository?.getMarket(object:OperationCallbackDetail<Market> {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onSuccess(data: Market?) {
                _isViewLoading.value = false
                if(data == null) {
                    _isEmptyList.value = true
                } else {
                    _market.value = data
                    val tempMarketChart = arrayListOf<MarketChart>()
                     data.prices.forEachIndexed { index, element ->
                         var current = LocalDateTime.now()
                         var substractDay = current.minusDays((index).toLong())
                        val ts = MarketChart(substractDay.toString(), element[1])
                         tempMarketChart.add(ts)
                    }
                    _marketChart.value = tempMarketChart

                }
            }

            override fun onError(error: String?) {
                _isViewLoading.value = false
                _onMessageError.value = error
            }

        }, id)
    }

}