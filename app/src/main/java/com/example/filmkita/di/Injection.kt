package com.example.filmkita.di

import com.example.filmkita.data.ApiClient
import com.example.filmkita.data.CoinDetailRemoteDataSource
import com.example.filmkita.data.CoinRemoteDataSource
import com.example.filmkita.data.MarketRemoteDataSource
import com.example.filmkita.model.datasource.CoinDataSource
import com.example.filmkita.model.datasource.CoinDetailDataSource
import com.example.filmkita.model.datasource.MarketDataSource
import com.example.filmkita.model.repository.CoinDetailRepository
import com.example.filmkita.model.repository.CoinRepository
import com.example.filmkita.model.repository.MarketRepository
import com.example.filmkita.viewmodel.CoinViewModelFactory

object Injection {

    private var coinDataSource: CoinDataSource? = null
    private var coinRepository: CoinRepository? = null
    private var coinViewModelFactory: CoinViewModelFactory? = null

    private var coinDetailDataSource: CoinDetailDataSource? = null
    private var coinDetailRepository: CoinDetailRepository? = null
    private var coinDetailViewModelFactory: CoinViewModelFactory? = null

    private var marketDataSource: MarketDataSource? = null
    private var marketRepository: MarketRepository? = null
    private var marketViewModelFactory: CoinViewModelFactory? = null

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

    private fun provideDataSource() = coinDataSource ?: createCoinDataSource()
    private fun provideRepository() = coinRepository ?: createCoinRepository()
    fun provideViewModelFactory() = coinViewModelFactory ?: createCoinFactory()

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

    private fun createMarketDataSource(): MarketDataSource {
        val dataSource = MarketRemoteDataSource(ApiClient)
        marketDataSource = dataSource
        return  dataSource
    }

    private fun createMarketRepository(): MarketRepository {
        val repository = MarketRepository(provideMarketDataSource())
        marketRepository = repository
        return repository
    }

    private fun createDetailCoinFactory(): CoinViewModelFactory {
        val factory = CoinViewModelFactory(coinRepository = null, coinDetailRepository = provideDetailRepository(),
            provideMarketRepository() )
        coinDetailViewModelFactory = factory
        marketViewModelFactory = factory
        return factory
    }

    private fun provideDetailDataSource() = coinDetailDataSource ?: createDetailCoinDataSource()
    private fun provideDetailRepository() = coinDetailRepository ?: createDetailCoinRepository()

    private fun provideMarketDataSource() = marketDataSource ?: createMarketDataSource()
    private fun provideMarketRepository() = marketRepository ?: createMarketRepository()

    fun provideCombineViewModelFacotry() = coinDetailViewModelFactory ?: createDetailCoinFactory()

    fun destory() {
        coinDataSource = null
        coinRepository = null
        coinViewModelFactory = null
        coinDetailDataSource = null
        coinDetailRepository = null
        coinDetailViewModelFactory = null
        marketDataSource = null
        marketRepository = null
        marketViewModelFactory = null
    }


}