# Searchable Selection and Chooser Dialog
Project for Chooser dialog, Searchable single and multi selection dialog

### Chooser dialog
Implement 'SelectionItem' in your list item model

```
data class SelectableData(var id: String, var name: String) : SelectionItem() {
    var appIcon: Int = -1
    override var code: String
        get() = id
        set(value) {
            id = value
        }
    override var text: String
        get() = name
        set(value) {
            name = value
        }
    override var icon: Int
        get() = appIcon
        set(value) {
            appIcon = value
        }
}
```
>Now create your list and call as follows-
```
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
	.showIcon(false)
        .title("Select an option")
        .show()
```
> You can change Chooser dialog style by the following style rules
```
  <style name="CustomChooserDialog" parent="Theme.AppCompat.Dialog">
        <item name="chooserDialog_titleStyle">@style/SelectionDialog_titleStyle</item>
        <item name="chooserDialog_itemStyle">@style/SelectionDialog_itemStyle</item>
    </style>

    <style name="SelectionDialog_titleStyle">
        <item name="android:textAppearance">?android:attr/textAppearanceSmall</item>
        <item name="android:gravity">center</item>
        <item name="android:textColor">@color/purple_500</item>
    </style>

    <style name="SelectionDialog_itemStyle">
        <item name="android:textAppearance">?android:attr/textAppearanceSmall</item>
        <item name="android:gravity">start</item>
        <item name="android:textStyle">normal</item>
        <item name="android:textColor">@color/purple_200</item>
    </style>
```
#### Searchable single and multi select dialog
Implement 'SearchableItem' in your list item model
```
data class SearchableData(var id: String, var name: String) : SearchableItem() {
    var checked: Boolean = false
    override var code: String
        get() = id
        set(value) {
            id = value
        }
    override var text: String
        get() = name
        set(value) {
            name = value
        }
    override var isSelected: Boolean
        get() = checked
        set(value) {
            checked = value
        }

}
```
>Now create your list and call as follows-
```
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
                .singleSelection(true)
                .cancelable(false)
                .setSearchViewIconified(false) // keep keyboard open and focused in searchview
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
```
> You can change searchable dialog style by the following style rules
```
  <style name="SearchableDialog_itemIconStyle">
        <item name="android:button">@drawable/searchable_item_icon</item>

   </style>
```
![ChooserDialog](https://user-images.githubusercontent.com/37567521/174001918-ff764a92-894b-4467-a380-8565170f45a8.png)
![SingleSelectionDialog](https://user-images.githubusercontent.com/37567521/174002002-102d9f2e-9929-4752-9aa5-ebf8a12e97af.png)
![MultiSelectionDialog](https://user-images.githubusercontent.com/37567521/174002021-fbe0776a-26d8-4e18-8f7b-949fc7fa69e8.png)

### How to add this into your project
>Add it in your root level `build.gradle` at the end of repositories:
```
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
>Add the dependency in your app level `build.gradle`
```
	dependencies {
	        implementation 'com.github.aburasel:SelectionDialog:1.2.0'
	}


```
