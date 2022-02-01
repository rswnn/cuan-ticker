package com.example.filmkita.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.filmkita.data.OperationCallback
import com.example.filmkita.data.OperationCallbackDetail
import com.example.filmkita.model.Coin
import com.example.filmkita.model.CoinDetail
import com.example.filmkita.model.CoinDetailRepository
import com.example.filmkita.model.CoinRepository
import com.example.filmkita.utils.WrappedResponseDetail

class CoinViewModel(private val coinRepository: CoinRepository?, private val coinDetailRepository: CoinDetailRepository?): ViewModel() {
    private val _coins = MutableLiveData<List<Coin>>().apply { value = emptyList() }
    val coins: LiveData<List<Coin>> = _coins

    private val _coinDetail = MutableLiveData<CoinDetail>().apply { value = null }
    val coinDetail: LiveData<CoinDetail> = _coinDetail

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
            override fun onSuccess(data: WrappedResponseDetail<CoinDetail>) {
                _isViewLoading.value = false
                Log.v("ViewMODEL", "$data")
                if (data.data == null) {
                    _isEmptyList.value = true // should handle obj if null
                } else {
                    _coinDetail.value = data.data
                }
            }

            override fun onError(error: String?) {
                _isViewLoading.value = false
                _onMessageError.value = error
            }

        }, id)
    }

}