package com.aci.selectiondialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ChooserDialog : Dialog {
    constructor(context: Context) : super(context, R.style.ThemeChooserDialog) {

    }

    constructor(context: Context, themeId: Int) : super(context, themeId) {

    }

    var selectableItems: List<SelectionItem>? = null
    var onItemSelectedListener: OnItemSelectedListener? = null
    var dialogTitle = context.resources.getString(R.string.select_option)
    var iconEnabled = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.chooser_dialog)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val titleTextView = findViewById<TextView>(R.id.titleTextView)
        titleTextView.text = dialogTitle

        val mLayoutManager = LinearLayoutManager(context)
        val adapter =
            SelectionRvAdapter(
                context,
                selectableItems ?: listOf(),
                object : SelectionRvAdapter.ItemClickListener {
                    override fun onItemClicked(item: SelectionItem, position: Int) {
                        onItemSelectedListener?.onCompleteSelection(item)
                        dismiss()
                    }

                },
                iconEnabled
            )
        recyclerView.itemAnimator = null
        recyclerView.layoutManager = mLayoutManager
        recyclerView.adapter = adapter

    }

    interface OnItemSelectedListener {
        fun onCompleteSelection(selectedItem: SelectionItem)
    }

    class Builder(private val context: Context) {
        private var title: String? = null
        private var items: List<SelectionItem> = emptyList()
        private var listener: OnItemSelectedListener? = null
        var showIcon = true

        fun title(title: String?) = apply {
            this.title = title
        }

        fun showIcon(show: Boolean) = apply {
            this.showIcon = show
        }

        fun items(items: List<SelectionItem>) = apply {
            this.items = items
        }

        fun <T> items(
            source: List<T>,
            mapper: (T) -> SelectionItem
        ) = items(source.map { item -> mapper.invoke(item) })


        fun onItemClickListener(listener: OnItemSelectedListener) = apply {
            this.listener = listener
        }

        private fun build() = ChooserDialog(context).apply {
            selectableItems = items
            iconEnabled = showIcon
            onItemSelectedListener = listener
            dialogTitle = title ?: context.resources.getString(R.string.select_option)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }

        fun show() {
            build().show()
        }
    }

}