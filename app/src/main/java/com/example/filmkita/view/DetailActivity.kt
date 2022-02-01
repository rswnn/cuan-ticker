package com.example.filmkita.view

import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.filmkita.R
import com.example.filmkita.constant.Constant
import com.example.filmkita.di.Injection
import com.example.filmkita.model.Coin
import com.example.filmkita.model.CoinDetail
import com.example.filmkita.view.adapter.ListFilmDetailAdapter
import com.example.filmkita.viewmodel.CoinViewModel
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import jp.wasabeef.glide.transformations.BlurTransformation

class DetailActivity : AppCompatActivity() {

    private lateinit var pieChart: PieChart

    private lateinit var rvFilms: RecyclerView

    private var listCoin: ArrayList<Coin> = arrayListOf()

    private val coinViewModel by viewModels<CoinViewModel> {
        Injection.provideDetailViewModelFactory()
    }

    private var tvTitle:TextView? = null
    private var tvDetail:TextView? = null
    private var tvLang:TextView? = null
    private var tvPopularity:TextView? = null
    private var tvReleaseDate:TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        tvTitle = findViewById(R.id.tv_title)
        tvDetail = findViewById(R.id.tv_detail)
        tvLang = findViewById(R.id.tv_lang)
        tvPopularity = findViewById(R.id.tv_popularity)
        tvReleaseDate = findViewById(R.id.tv_release_date)
        var newDrawable = R.drawable.ic_back
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""

        rvFilms = findViewById(R.id.rv_film_vertical)

//        listCoin.addAll(FilmData.listData)

        showRecyclerList()
        setupViewModel()

//        pieChart = findViewById(R.id.pieChart)


//        intent.getStringExtra(Constant.EXTRA_BG)?.let { renderBackground(it) }
//
//        intent.getStringExtra(Constant.EXTRA_VOTE_AVG)?.let {
//            setDataToPieChart(it)
//        }


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
            .into(imgFg)
    }

    private fun renderBackground (img:String) {
        val imgBg:ImageView = findViewById(R.id.img_background)
        Glide.with(this)
            .load(img)
            .apply(RequestOptions.bitmapTransform(BlurTransformation(25, 3)))
            .into(imgBg)
    }

    private fun setDataToPieChart(dt:String) {
      val tf = Typeface.createFromAsset(this.assets, "poppins_regular.ttf")
        var dtAvg = dt.toFloat()
        pieChart.setUsePercentValues(true)
        var maxAvg = 10
        val dataEntries = ArrayList<PieEntry>()
        dataEntries.add(PieEntry(dtAvg, ))
        dataEntries.add(PieEntry(maxAvg % dtAvg ))

        val colors: ArrayList<Int> = ArrayList()
        colors.add(Color.parseColor("#3E8E7E"))
        colors.add(Color.parseColor("#F05454"))

        val dataSet = PieDataSet(dataEntries, "")
        val data = PieData(dataSet)

        // In Percentage
        data.setValueFormatter(PercentFormatter())
        data.setValueTypeface(tf)
        data.setValueTextColor(R.color.black)
        dataSet.sliceSpace = 3f
        dataSet.colors = colors
        pieChart.data = data
        data.setValueTextSize(15f)
        pieChart.setExtraOffsets(5f, 10f, 5f, 5f)
        pieChart.animateY(1400, Easing.EaseInOutQuad)

        //create hole in center
        pieChart.holeRadius = 58f
        pieChart.transparentCircleRadius = 61f
        pieChart.isDrawHoleEnabled = true
        pieChart.setHoleColor(Color.WHITE)


        //add text in center
        pieChart.setDrawCenterText(true);
        pieChart.centerText = "VOTE AVERAGE"
        pieChart.setCenterTextTypeface(tf)
        pieChart.legend.isEnabled = false
        pieChart.description.isEnabled = false


        pieChart.invalidate()

    }

    private fun setupViewModel() {
        coinViewModel.coinDetail.observe(this, renderDetailCoin)
    }

    private val renderDetailCoin = Observer<CoinDetail> {
        Log.v("CONSOLE RENDER DETAIL", "$it")
        it?.let {
            tvTitle?.text = it.name
            tvDetail?.text = it.description.toString()
            renderForeGround(it.image.large)
        }
    }

    override fun onResume() {
        super.onResume()
        val id = intent.getStringExtra(Constant.EXTRA_ID)
        id?.let { coinViewModel.loadCoinDetail(it) }
    }

    private fun showRecyclerList() {
        rvFilms.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val listCoinAdapter = ListFilmDetailAdapter(listCoin)
        rvFilms.adapter = listCoinAdapter
    }

    override fun onDestroy() {
        super.onDestroy()
        Injection.destory()
    }
}
