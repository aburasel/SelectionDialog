package com.aci.selectiondialog

//
// Created by RASEL on 12-Jun-22.

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