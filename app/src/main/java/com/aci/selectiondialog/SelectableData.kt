package com.aci.selectiondialog

//
// Created by RASEL on 12-Jun-22.

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