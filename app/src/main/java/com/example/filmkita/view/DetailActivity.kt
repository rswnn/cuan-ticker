package com.example.filmkita.view

import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.core.text.HtmlCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.filmkita.R
import com.example.filmkita.constant.Constant
import com.example.filmkita.di.Injection
import com.example.filmkita.model.Coin
import com.example.filmkita.model.CoinDetail
import com.example.filmkita.model.Market
import com.example.filmkita.model.MarketChart
import com.example.filmkita.view.adapter.ListFilmDetailAdapter
import com.example.filmkita.viewmodel.CoinViewModel
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.PercentFormatter
import jp.wasabeef.glide.transformations.BlurTransformation

class DetailActivity : AppCompatActivity() {

    private lateinit var lineChart: LineChart
    private lateinit var rvFilms: RecyclerView
    private var tvTitle:TextView? = null
    private var tvDetail:TextView? = null
    private var tvLang:TextView? = null
    private var tvPopularity:TextView? = null
    private var tvReleaseDate:TextView? = null
    private val chartData = ArrayList<Entry>()

    private var listCoin: ArrayList<Coin> = arrayListOf()
    private val coinViewModel by viewModels<CoinViewModel> {
        Injection.provideCombineViewModelFacotry()
    }


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        tvTitle = findViewById(R.id.tv_title)
        tvDetail = findViewById(R.id.tv_detail)
        tvLang = findViewById(R.id.tv_lang)
        tvPopularity = findViewById(R.id.tv_popularity)
        tvReleaseDate = findViewById(R.id.tv_release_date)
        lineChart = findViewById(R.id.lineChart)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""

//        rvFilms = findViewById(R.id.rv_film_vertical)
        renderBackground()
        setupViewModel()

        showRecyclerList()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }


    private fun renderForeGround (img:String) {
        val imgFg:ImageView = findViewById(R.id.img_foreground)
        Glide.with(this)
            .load(img)
            .apply(RequestOptions().override(150, 150))
            .into(imgFg)
    }

    private fun renderBackground () {
        val imgBg:ImageView = findViewById(R.id.img_background)
        Glide.with(this)
            .load(R.drawable.blockchain)
            .apply(RequestOptions.bitmapTransform(BlurTransformation(20, 3)))
            .into(imgBg)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun setupViewModel() {
        coinViewModel.coinDetail.observe(this, renderDetailCoin)
        coinViewModel.market.observe(this, renderMarket)
        coinViewModel.marketChart.observe(this, renderMarketChart)
        coinViewModel.isViewLoading.observe(this, isViewLoadingObserver)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private val renderDetailCoin = Observer<CoinDetail> {
        it?.let {
            tvTitle?.text = it.name
            tvDetail?.text = Html.fromHtml(it.description.en, HtmlCompat.FROM_HTML_MODE_LEGACY)
            tvDetail?.movementMethod = LinkMovementMethod.getInstance()
            var rank = intent.getStringExtra(Constant.EXTRA_RANK)
            tvLang?.text = "#$rank"
            tvPopularity?.text = intent.getStringExtra(Constant.EXTRA_ORIGINAL_LANG)
            tvReleaseDate?.text = intent.getStringExtra(Constant.EXTRA_POPULARITY)
            renderForeGround(it.image.large)
        }
    }

    private val renderMarketChart = Observer<List<MarketChart>> {
        it?.let {
            it.forEachIndexed { index, marketChart ->
                chartData.add(Entry(index.toFloat(), marketChart.price.toFloat()))
            }
            renderLineChart()
        }
    }

    private val renderMarket = Observer<Market> {
        it?.let {

        }
    }

    private val isViewLoadingObserver = Observer<Boolean> {
    }

    override fun onResume() {
        super.onResume()
        val id = intent.getStringExtra(Constant.EXTRA_ID)
        id?.let {
            coinViewModel.loadCoinDetail(it)
            coinViewModel.loadMarket(it)
        }
    }

    private fun showRecyclerList() {
//        rvFilms.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
//        val listCoinAdapter = ListFilmDetailAdapter(listCoin)
//        rvFilms.adapter = listCoinAdapter
    }

    private fun renderLineChart () {
        if (chartData.size > 0) {
            lineChart.visibility = View.VISIBLE
            val marketChartData = LineDataSet(chartData, "Price")
            marketChartData.mode = LineDataSet.Mode.CUBIC_BEZIER
            marketChartData.color = Color.GREEN
            marketChartData.lineWidth = 1.5F
            marketChartData.setCircleColor(Color.GREEN)
            marketChartData.setDrawValues(false)

            val legend = lineChart.legend
            legend.isEnabled = true
            legend.verticalAlignment = Legend.LegendVerticalAlignment.TOP
            legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
            legend.orientation = Legend.LegendOrientation.HORIZONTAL
            legend.setDrawInside(false)

            lineChart.xAxis.setDrawGridLines(false)
            lineChart.xAxis.setDrawGridLinesBehindData(false)
            lineChart.axisLeft.setDrawGridLines(false)
            lineChart.axisLeft.isEnabled = false
            lineChart.y

            lineChart.description.isEnabled = false
            lineChart.xAxis.isEnabled = false
            lineChart.data = LineData(marketChartData)
            lineChart.animateXY(100, 500)
            lineChart.setVisibleXRangeMaximum(40F)
            lineChart.moveViewToX(90F)
        } else {
            lineChart.visibility = View.GONE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Injection.destory()
    }
}
