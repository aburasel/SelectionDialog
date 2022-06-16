package com.aci.selectiondialog
// Created by RASEL on 12-Jun-22.

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SearchableRvAdapter(
    private var mContext: Context,
    private var itemList: List<SearchableItem>,
    private var filteredList: List<SearchableItem>,
    val searchInCodeAlso: Boolean,
    val searchOnlyOnStartsWith: Boolean,
    clickListener: ItemClickListener
) : Filterable, RecyclerView.Adapter<SearchableRvAdapter.ViewHolder>() {
    init {
        itemClickListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_searchable_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mItem = filteredList[holder.adapterPosition]

        holder.checkBox.setOnCheckedChangeListener(null)
        holder.titleTextView.text = holder.mItem!!.text
        holder.checkBox.isChecked = holder.mItem!!.isSelected

        /*if (position != filteredList.size - 1) {
            holder.itemView.background =
                ContextCompat.getDrawable(mContext, R.drawable.bg_row_border_bottom)
        } else {
            holder.itemView.setBackgroundColor(Color.WHITE)
        }*/

        holder.checkBox.setOnCheckedChangeListener { _, isChecked ->
            itemClickListener.onItemClicked(
                filteredList[holder.layoutPosition],
                isChecked
            )
        }

        holder.itemView.setOnClickListener { view ->
            holder.checkBox.isChecked = !holder.checkBox.isChecked
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charString = charSequence.toString()
                if (charString.isEmpty()) {
                    filteredList = itemList
                } else {
                    val tempList = ArrayList<SearchableItem>()
                    for (row in itemList) {
                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (searchInCodeAlso) {
                            if (searchOnlyOnStartsWith) {
                                if (row.text.lowercase()
                                        .startsWith(charString.lowercase()) ||
                                    row.code.lowercase()
                                        .startsWith(charString.lowercase())
                                ) {
                                    tempList.add(row)
                                }
                            } else {
                                if (row.text.lowercase()
                                        .contains(charString.lowercase()) ||
                                    row.code.lowercase()
                                        .contains(charString.lowercase())
                                ) {
                                    tempList.add(row)
                                }
                            }

                        } else {
                            if (searchOnlyOnStartsWith) {
                                if (row.text.lowercase()
                                        .startsWith(charString.lowercase())
                                ) {
                                    tempList.add(row)
                                }
                            } else {
                                if (row.text.lowercase()
                                        .contains(charString.lowercase())
                                ) {
                                    tempList.add(row)
                                }
                            }

                        }

                    }

                    filteredList = tempList
                }

                val filterResults = FilterResults()
                filterResults.values = filteredList
                return filterResults
            }

            override fun publishResults(
                charSequence: CharSequence,
                filterResults: FilterResults
            ) {
                filteredList = filterResults.values as List<SearchableItem>

                // refresh the list with filtered data
                notifyDataSetChanged()
            }
        }
    }

    override fun getItemCount(): Int {
        return filteredList.size
    }

    inner class ViewHolder(mView: View) : RecyclerView.ViewHolder(mView) {

        internal var titleTextView = mView.findViewById<TextView>(R.id.titleTextView)
        internal var checkBox = mView.findViewById<CheckBox>(R.id.checkBox)
        var mItem: SearchableItem? = null
    }

    companion object {
        lateinit var itemClickListener: ItemClickListener
    }


    interface ItemClickListener {
        fun onItemClicked(item: SearchableItem, b: Boolean)
    }
}