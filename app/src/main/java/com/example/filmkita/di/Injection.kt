package com.example.filmkita.di

import com.example.filmkita.data.ApiClient
import com.example.filmkita.data.CoinDetailRemoteDataSource
import com.example.filmkita.data.CoinRemoteDataSource
import com.example.filmkita.model.CoinDataSource
import com.example.filmkita.model.CoinDetailDataSource
import com.example.filmkita.model.CoinDetailRepository
import com.example.filmkita.model.CoinRepository
import com.example.filmkita.viewmodel.CoinViewModelFactory

object Injection {

    private var coinDataSource: CoinDataSource? = null
    private var coinRepository: CoinRepository? = null
    private var coinViewModelFactory: CoinViewModelFactory? = null

    private var coinDetailDataSource: CoinDetailDataSource? = null
    private var coinDetailRepository: CoinDetailRepository? = null
    private var coinDetailViewModelFactory: CoinViewModelFactory? = null

    private fun createCoinDataSource(): CoinDataSource {
        val dataSource = CoinRemoteDataSource(ApiClient)
        coinDataSource = dataSource
        return  dataSource
    }

    private fun createCoinRepository(): CoinRepository {
        val repository = CoinRepository(provideDataSource())
        coinRepository = repository
        return repository
    }

    private fun createCoinFactory(): CoinViewModelFactory {
        val factory = CoinViewModelFactory(provideRepository())
        coinViewModelFactory = factory
        return factory
    }

    private fun createDetailCoinDataSource(): CoinDetailDataSource {
        val dataSource = CoinDetailRemoteDataSource(ApiClient)
        coinDetailDataSource = dataSource
        return  dataSource
    }

    private fun createDetailCoinRepository(): CoinDetailRepository {
        val repository = CoinDetailRepository(provideDetailDataSource())
        coinDetailRepository = repository
        return repository
    }

    private fun createDetailCoinFactory(): CoinViewModelFactory {
        val factory = CoinViewModelFactory(coinRepository = null, coinDetailRepository = provideDetailRepository())
        coinDetailViewModelFactory = factory
        return factory
    }

    private fun provideDataSource() = coinDataSource ?: createCoinDataSource()
    private fun provideRepository() = coinRepository ?: createCoinRepository()
    fun provideViewModelFactory() = coinViewModelFactory ?: createCoinFactory()

    private fun provideDetailDataSource() = coinDetailDataSource ?: createDetailCoinDataSource()
    private fun provideDetailRepository() = coinDetailRepository ?: createDetailCoinRepository()
    fun provideDetailViewModelFactory() = coinDetailViewModelFactory ?: createDetailCoinFactory()

    fun destory() {
        coinDataSource = null
        coinRepository = null
        coinViewModelFactory = null
        coinDetailDataSource = null
        coinDetailRepository = null
        coinDetailViewModelFactory = null
    }


}