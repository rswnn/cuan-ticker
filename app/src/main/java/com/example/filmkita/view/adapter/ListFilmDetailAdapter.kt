package com.example.filmkita.view.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.filmkita.view.DetailActivity
import com.example.filmkita.R
import com.example.filmkita.model.Coin

class ListFilmDetailAdapter(private val listCoin: ArrayList<Coin>): RecyclerView.Adapter<ListFilmDetailAdapter.ListViewHolder>() {




    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvName: TextView = itemView.findViewById(R.id.tv_item_name_vertical)
        var tvDetail: TextView = itemView.findViewById(R.id.tv_item_detail_vertical)
        var imgPhoto: ImageView = itemView.findViewById(R.id.img_item_photo_vertical)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_card_vertical, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        Glide.with(holder.itemView.context)
            .load( listCoin[position].image)
            .apply(RequestOptions().override(350, 550))
            .into(holder.imgPhoto)
        holder.tvName.text = listCoin[position].current_price.toString()
        holder.tvDetail.text = listCoin[position].market_cap_change_percentage_24h.toString()
        holder.itemView.setOnClickListener {
            val ctx = holder.itemView.context
            val moveIntent = Intent(holder.itemView.context, DetailActivity::class.java)
//            moveIntent.putExtra(Constant.EXTRA_FG,listCoin[position].poster_path)
//            moveIntent.putExtra(Constant.EXTRA_BG,listCoin[position].backdrop_path)
//            moveIntent.putExtra(Constant.EXTRA_TITLE, listCoin[position].title)
//            moveIntent.putExtra(Constant.EXTRA_DETAIL, listCoin[position].overview)
//            moveIntent.putExtra(Constant.EXTRA_ORIGINAL_LANG, listCoin[position].original_language)
//            moveIntent.putExtra(Constant.EXTRA_POPULARITY, listCoin[position].popularity.toString())
//            moveIntent.putExtra(Constant.EXTRA_RELEASE_DATE, listCoin[position].release_date)
//            moveIntent.putExtra(Constant.EXTRA_VOTE_AVG, listCoin[position].vote_average.toString())
//            moveIntent.putExtra(Constant.EXTRA_VOTE_COUNT, listCoin[position].vote_count.toString())
            ctx.startActivity(moveIntent)
        }
    }

    override fun getItemCount(): Int {
        return  listCoin.size
    }

}