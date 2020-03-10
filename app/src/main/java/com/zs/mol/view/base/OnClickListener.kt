package com.zs.mol.view.base

import android.view.View

abstract class OnClickItemListener<T> {
    abstract fun onClickItem(item: T?)
    open fun onMouseOver(view : View, item: T?, isOn: Boolean): Boolean {
        return false
    }
}