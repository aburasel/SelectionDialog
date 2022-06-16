package com.aci.selectiondialog

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SearchableSelectionDialog {

    interface MultiSelectionDoneListener {
        fun onCompleteSelection(selectedItems: ArrayList<SearchableItem>)
    }

    interface SingleSelectionDoneListener {
        fun onCompleteSelection(selectedItem: SearchableItem)
    }

    class Builder(private val context: Context) {
        private var title: String? = null
        private var okButtonText: String = context.resources.getString(R.string.ok)
        private var cancelButtonText: String = context.resources.getString(R.string.cancel)
        private var items: List<SearchableItem> = emptyList()
        private var preSelectedCodeList: List<String> = NO_SELECT
        private var searchInCodeAlso: Boolean = false
        private var searchOnlyAtBeginning: Boolean = false
        private var singleSelection: Boolean = false
        private var cancelable: Boolean = true
        private var multiSelectListener: MultiSelectionDoneListener? = null
        private var singleSelectListener: SingleSelectionDoneListener? = null


        fun title(title: String?) = apply {
            this.title = title
        }

        fun singleSelection(flag: Boolean) = apply {
            this.singleSelection = flag
        }

        fun cancelable(flag: Boolean) = apply {
            this.cancelable = flag
        }

        fun searchInCodeAlso(flag: Boolean) = apply {
            this.searchInCodeAlso = flag
        }

        fun searchOnlyAtBeginning(flag: Boolean) = apply {
            this.searchOnlyAtBeginning = flag
        }

        fun okButtonText(okButtonText: String) = apply {
            this.okButtonText = okButtonText
        }

        fun cancelButtonText(cancelButtonText: String) = apply {
            this.cancelButtonText = cancelButtonText
        }

        fun preSelectedCodes(codes: List<String>) = apply {
            this.preSelectedCodeList = codes
        }

        fun items(items: List<SearchableItem>) = apply {
            this.items = items
        }

        fun <T> items(
            source: List<T>,
            mapper: (T) -> SearchableItem
        ) = items(source.map { item -> mapper.invoke(item) })

        fun onMultiSelectionDoneListener(listener: MultiSelectionDoneListener) = apply {
            this.multiSelectListener = listener
        }

        fun onSingleSelectionDoneListener(listener: SingleSelectionDoneListener) = apply {
            this.singleSelectListener = listener
        }

        var dialog: AlertDialog? = null
        private fun build() = AlertDialog.Builder(context).apply {
            val inflater = LayoutInflater.from(context)
            val convertView = inflater.inflate(R.layout.searchable_dialog, null)
            setView(convertView)

            val recyclerView = convertView.findViewById<RecyclerView>(R.id.recyclerView)
            val titleTextView = convertView.findViewById<TextView>(R.id.titleTextView)
            val searchView = convertView.findViewById<SearchView>(R.id.searchView)

            searchView.setIconifiedByDefault(true)
            searchView.isFocusable = true
            searchView.isIconified = false
            searchView.requestFocusFromTouch()
            titleTextView.text = title

            if (singleSelection.not()) {
                try {
                    items.filter { it.code in preSelectedCodeList }.map {
                        it.isSelected = true
                    }
                } catch (e: NoSuchElementException) {
                    Toast.makeText(context, "Pre selected code does not exist", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            val mLayoutManager = LinearLayoutManager(context)
            val adapter =
                SearchableRvAdapter(
                    context,
                    items,
                    items,
                    searchInCodeAlso,
                    searchOnlyAtBeginning,
                    object : SearchableRvAdapter.ItemClickListener {
                        override fun onItemClicked(item: SearchableItem, b: Boolean) {
                            if (singleSelection.not()) {
                                val pos = items.indexOfFirst { it.code == item.code }
                                pos.let {
                                    if (pos >= 0) {
                                        items[pos].isSelected = b
                                    }
                                }
                            } else {
                                singleSelectListener?.onCompleteSelection(item)
                                dialog?.dismiss()
                            }

                        }
                    }
                )
            recyclerView.itemAnimator = null
            recyclerView.layoutManager = mLayoutManager
            recyclerView.adapter = adapter
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    // do something on text submit
                    return false
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    // do something when text changes
                    adapter.filter.filter(newText)
                    return false
                }
            })
            /**
             * positive button only for multiselect
             */
            if (singleSelection.not()) {
                setPositiveButton(okButtonText) { dialogInterface, i ->
                    dialogInterface.dismiss()
                    val resultList = ArrayList<SearchableItem>()
                    resultList.addAll(items.filter { it.isSelected })
                    multiSelectListener?.onCompleteSelection(resultList)
                }
                setNegativeButton(cancelButtonText) { dialogInterface, i ->
                    dialogInterface.dismiss()
                }
            }

        }

        fun show() {
            dialog = build().setCancelable(cancelable).show()
        }
    }

    companion object {
        val NO_SELECT = listOf<String>()
    }
}