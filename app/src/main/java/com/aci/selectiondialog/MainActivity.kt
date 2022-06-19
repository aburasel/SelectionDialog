package com.aci.selectiondialog

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button = findViewById<Button>(R.id.button)
        val button2 = findViewById<Button>(R.id.button2)
        button.setOnClickListener {
            val a = arrayListOf<SelectableData>()
            a.add(SelectableData("1", "Mohammad"))
            a.add(SelectableData("2", "Ahmed"))
            a.add(SelectableData("3", "Yasin"))

            ChooserDialog.Builder(this)
                .items(a)
                .onItemClickListener(object : ChooserDialog.OnItemSelectedListener {
                    override fun onCompleteSelection(selectedItem: SelectionItem) {
                        Toast.makeText(this@MainActivity, selectedItem.text, Toast.LENGTH_LONG)
                            .show()
                    }
                })
                .title("Select an option")
                .show()
        }
        button2.setOnClickListener {
            val searchableItems = arrayListOf<SearchableData>()
            searchableItems.add(SearchableData("1", "Emmet lorem"))
            searchableItems.add(SearchableData("2", "Just type lorem"))
            searchableItems.add(SearchableData("3", "Html to generate a paragraph"))
            searchableItems.add(SearchableData("4", "Control how much text"))
            searchableItems.add(SearchableData("5", "Generated with a number suffix"))
            searchableItems.add(SearchableData("6", "Generate 10 words of dummy text"))
            searchableItems.add(SearchableData("7", "Combine lorem with other Emmet "))
            SearchableSelectionDialog.Builder(this)
                .items(searchableItems)
                .title("Search Items")
                .preSelectedCodes(listOf("3", "4"))// Add for SingleSelection = false only
                .okButtonText("Submit")// Add for SingleSelection = false only
                .cancelButtonText("Cancel")// Add for SingleSelection = false only
                .searchInCodeAlso(false)
                .searchOnlyAtBeginning(false)
                .singleSelection(false)
                .cancelable(false)
                .onMultiSelectionDoneListener(object :
                    SearchableSelectionDialog.MultiSelectionDoneListener {
                    override fun onCompleteSelection(selectedItems: ArrayList<SearchableItem>) {
                        Log.e("multi-select", selectedItems.toString())
                    }
                })
                .onSingleSelectionDoneListener(object :
                    SearchableSelectionDialog.SingleSelectionDoneListener {
                    override fun onCompleteSelection(selectedItem: SearchableItem) {
                        Log.e("single-select", selectedItem.toString())
                    }

                })
                .show()
        }


    }
}