package com.zs.mol.view.base.selection

interface SelectView {
    fun getTitle(): String
    fun getMessage() : String
    fun getSelectionList() : List<SelectionItem>
}