package com.example.filmkita.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.filmkita.viewmodel.CoinViewModel
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.filmkita.DialogModal
import com.example.filmkita.R
import com.example.filmkita.constant.Constant
import com.example.filmkita.di.Injection
import com.example.filmkita.model.Coin
import com.example.filmkita.view.adapter.ListFilmAdapter

class MainActivity : AppCompatActivity() {

    private val TAG = "CONSOLE"

    private val coinViewModel by viewModels<CoinViewModel> {
        Injection.provideViewModelFactory()
    }

    private lateinit var rvFilms: RecyclerView
    private lateinit var adapter: ListFilmAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvFilms = findViewById(R.id.rv_film)

        setupViewModel()
        showRecyclerList()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val intent = Intent(this, MyProfile::class.java)
        startActivity(intent)

        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
            menuInflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }


    private fun showRecyclerList() {
        rvFilms.layoutManager = LinearLayoutManager(this)
        adapter = ListFilmAdapter(coinViewModel.coins.value ?: emptyList())
        adapter.setOnItemCallback(object : ListFilmAdapter.OnItemClickCallback {
            override fun onItemClicked(img:String) {
                var bundle = Bundle()
                bundle.putString(Constant.EXTRA_FG, img)
                DialogModal().apply {
                    arguments = bundle
                }.show(supportFragmentManager, "DialogModal")
            }

        })
        rvFilms.adapter = adapter
    }

    private fun setupViewModel() {
        coinViewModel.coins.observe(this, renderCoins)
    }

    private val renderCoins = Observer<List<Coin>> {
        Log.v(TAG, "Data updated $it")
        adapter.update(it)
    }

    override fun onResume() {
        super.onResume()
        coinViewModel.loadCoins()
    }

    override fun onDestroy() {
        super.onDestroy()
        Injection.destory()
    }

}