package com.zs.mol.view.unit.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zs.mol.model.unit.BattleUnit

class UnitDetailViewModel : ViewModel() {
    val unit: MutableLiveData<BattleUnit> = MutableLiveData()

    fun rename(name: String) {
        unit.value?.setName(name)
        unit.value = unit.value
    }
}
