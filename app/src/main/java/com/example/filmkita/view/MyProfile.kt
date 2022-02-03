package com.example.filmkita.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.filmkita.R

class MyProfile : AppCompatActivity() {

    private lateinit var imgProfile:ImageView
    private lateinit var imgIconBack:ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_profile)
        imgProfile = findViewById(R.id.img_my_profile)
        imgIconBack = findViewById(R.id.icon_back)
        supportActionBar?.title = ""
        supportActionBar?.hide()

        renderImgProfile()
        navigateBack()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun renderImgProfile() {
        Glide.with(this)
            .load(R.drawable.bread)
            .apply(RequestOptions().override(350, 350))
            .circleCrop()
            .into(imgProfile)
    }

    private fun navigateBack () {
        imgIconBack.setOnClickListener {
            this.finish()
        }
    }
}