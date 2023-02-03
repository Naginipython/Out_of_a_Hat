package com.example.out_of_a_hat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.out_of_a_hat.databinding.ActivityHatBinding
import com.example.out_of_a_hat.databinding.ItemHatItemBinding

class HatItemAdapter (private var hatItems: List<String>): RecyclerView.Adapter<HatItemAdapter.HatItemViewHolder>() {
    inner class HatItemViewHolder(val binding: ItemHatItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HatItemViewHolder {
        val layout = LayoutInflater.from(parent.context)
        val binding = ItemHatItemBinding.inflate(layout, parent, false)
        return HatItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return hatItems.size
    }

    override fun onBindViewHolder(holder: HatItemViewHolder, position: Int) {
        holder.binding.apply {
            tvItem.text = hatItems[position]
        }
    }
}