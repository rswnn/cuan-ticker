package com.example.filmkita.view.adapter

import android.content.Intent
import android.graphics.Color
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
import com.example.filmkita.constant.Constant
import com.example.filmkita.model.Coin
import kotlin.math.round

class ListFilmAdapter(private var listCoin: List<Coin>): RecyclerView.Adapter<ListFilmAdapter.ListViewHolder>() {


    private lateinit var onItemClickCallback: OnItemClickCallback


    fun setOnItemCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }


    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvName: TextView = itemView.findViewById(R.id.tv_item_name)
        var tvDetail: TextView = itemView.findViewById(R.id.tv_item_detail)
        var tvPercentage:TextView = itemView.findViewById(R.id.tv_item_percentage)
        var imgPhoto: ImageView = itemView.findViewById(R.id.img_item_photo)
    }

    fun update(data: List<Coin>) {
        listCoin = data
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_card_view_film, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        Glide.with(holder.itemView.context)
            .load( listCoin[position].image)
            .apply(RequestOptions().override(150, 150))
            .into(holder.imgPhoto)
        var currentPrice =  listCoin[position].current_price.toBigDecimal().toString()
        var percent = round(listCoin[position].market_cap_change_percentage_24h)
        var percetage = percent.toInt()
        holder.tvName.text = listCoin[position].symbol.uppercase()
        holder.tvDetail.text ="$$currentPrice"
        if(percetage > 0) {
            holder.tvPercentage.setTextColor(Color.parseColor( "#2EB086"))

        } else if (percetage < 0) {
            holder.tvPercentage.setTextColor(Color.parseColor("#B8405E"))
        }
        holder.tvPercentage.text = "%$percent"
        holder.itemView.setOnClickListener {
            val ctx = holder.itemView.context
            val moveIntent = Intent(holder.itemView.context, DetailActivity::class.java)
            moveIntent.putExtra(Constant.EXTRA_ID, listCoin[position].id)
            moveIntent.putExtra(Constant.EXTRA_RANK, listCoin[position].market_cap_rank.toString())
            moveIntent.putExtra(Constant.EXTRA_ORIGINAL_LANG, listCoin[position].circulating_supply.toLong().toString())
            moveIntent.putExtra(Constant.EXTRA_POPULARITY, listCoin[position].max_supply.toLong().toString())
            ctx.startActivity(moveIntent)
        }

        holder.imgPhoto.setOnClickListener {
//            onItemClickCallback.onItemClicked(tmdbCDN + listCoin[position].poster_path)
        }
    }

    override fun getItemCount(): Int {
        return  listCoin.size
    }

    interface OnItemClickCallback {
        fun onItemClicked(img:String)
    }

}