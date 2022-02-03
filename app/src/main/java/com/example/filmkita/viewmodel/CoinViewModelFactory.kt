package com.example.filmkita.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.filmkita.model.repository.CoinDetailRepository
import com.example.filmkita.model.repository.CoinRepository
import com.example.filmkita.model.repository.MarketRepository

class CoinViewModelFactory(private val coinRepository: CoinRepository? = null, private val coinDetailRepository: CoinDetailRepository? = null, private val marketRepository:MarketRepository? = null) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T:ViewModel?> create(modelClass: Class<T>): T {
        return  CoinViewModel(coinRepository, coinDetailRepository, marketRepository) as T
    }
}