package com.example.filmkita.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.filmkita.model.CoinDetailRepository
import com.example.filmkita.model.CoinRepository

class CoinViewModelFactory(private val coinRepository: CoinRepository? = null, private val coinDetailRepository: CoinDetailRepository? = null) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T:ViewModel?> create(modelClass: Class<T>): T {
        return  CoinViewModel(coinRepository, coinDetailRepository) as T
    }
}