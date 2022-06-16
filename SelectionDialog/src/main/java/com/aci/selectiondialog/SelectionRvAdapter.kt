package com.aci.selectiondialog
// Created by RASEL on 12-Jun-22.

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class SelectionRvAdapter(
    internal var mContext: Context,
    private var itemList: List<SelectionItem>,
    clickListener: ItemClickListener
) : RecyclerView.Adapter<SelectionRvAdapter.ViewHolder>() {
    init {
        itemClickListener = clickListener
    }

    inner class ViewHolder(mView: View) : RecyclerView.ViewHolder(mView) {

        internal var titleTextView = mView.findViewById<TextView>(R.id.titleTextView)
        internal var iconImageView = mView.findViewById<ImageView>(R.id.iconImageView)
        var mItem: SelectionItem? = null
        fun bind(item: SelectionItem, position: Int, listener: ItemClickListener) {
            itemView.setOnClickListener {
                listener.onItemClicked(item, position)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_chooser_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mItem = itemList[holder.adapterPosition]
        holder.bind(holder.mItem!!, position, itemClickListener)

        holder.titleTextView.text = holder.mItem!!.text
        if (holder.mItem!!.icon == -1) {
            holder.iconImageView.setImageResource(R.drawable.ic_baseline_radio_button_unchecked_24)
        } else {
            holder.iconImageView.setImageResource(holder.mItem!!.icon)
        }
        if (position != itemList.size - 1) {
            holder.itemView.background =
                ContextCompat.getDrawable(mContext, R.drawable.bg_row_border_bottom)
        } else {
            holder.itemView.setBackgroundColor(Color.WHITE)
        }

    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    companion object {
        lateinit var itemClickListener: ItemClickListener
    }


    interface ItemClickListener {
        fun onItemClicked(item: SelectionItem, position: Int)
    }
}