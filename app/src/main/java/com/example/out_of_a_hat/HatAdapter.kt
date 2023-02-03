package com.example.out_of_a_hat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.out_of_a_hat.databinding.ItemHatBinding

class HatAdapter (private var hats: List<Hats>) : RecyclerView.Adapter<HatAdapter.HatViewHolder>() {
    class HatViewHolder (private val itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById<TextView>(R.id.tvTitle)
        val view = itemView
    }

    //A settable closure
    var onItemClick: ((Hats) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HatViewHolder {
//        val layoutInflater = LayoutInflater.from(parent.context)
//        val binding = ItemHatBinding.inflate(layoutInflater, parent, false)
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_hat, parent, false)
        return HatViewHolder(view)
    }

    override fun onBindViewHolder(holder: HatViewHolder, position: Int) {
        holder.title.text = hats[position].name
        holder.view.setOnClickListener {
            onItemClick?.invoke(hats[position])
        }
    }

    override fun getItemCount(): Int {
        return hats.size
    }
}