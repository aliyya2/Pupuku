package com.example.pupuku.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.pupuku.R
import com.example.pupuku.model.Fertilizer

class FertilizerListAdapter(
    private val onItemClickListener: (Fertilizer) -> Unit
): ListAdapter<Fertilizer, FertilizerListAdapter.FertilizerViewHolder>(WORDS_COMPARATOR) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FertilizerViewHolder {
        return FertilizerViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: FertilizerViewHolder, position: Int) {
        val fertilizer = getItem(position)
        holder.bind(fertilizer)
        holder.itemView.setOnClickListener {
            onItemClickListener(fertilizer)
        }
    }
    class FertilizerViewHolder(itemView: View): RecyclerView.ViewHolder (itemView){
        private val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        private val addressTextView: TextView = itemView.findViewById(R.id.addressTextView)
        private val typeTextView: TextView = itemView.findViewById(R.id.typeEditText)

        fun bind(fertilizer: Fertilizer?) {
            nameTextView.text = fertilizer?.name
            addressTextView.text = fertilizer?.address
            typeTextView.text = fertilizer?.type
        }
    companion object{
        fun create(parent: ViewGroup): FertilizerListAdapter.FertilizerViewHolder{
            val view: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_fertilizer,parent, false)
            return FertilizerViewHolder(view)
            }
        }
    }

companion object{
    private val WORDS_COMPARATOR = object : DiffUtil.ItemCallback<Fertilizer>(){
        override fun areItemsTheSame(oldItem: Fertilizer, newItem: Fertilizer): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Fertilizer, newItem: Fertilizer): Boolean {
            return oldItem.id ==newItem.id
        }
    }
}
}

private fun FertilizerListAdapter.FertilizerViewHolder.bind(fertilizer: Fertilizer?) {

}
