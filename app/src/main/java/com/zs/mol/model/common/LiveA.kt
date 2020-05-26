package com.zs.mol.model.common

import androidx.lifecycle.MutableLiveData

class DefaultLiveData<T>(default: T) : MutableLiveData<T>(default) {
    override fun getValue(): T {
        return super.getValue()!!
    }
}