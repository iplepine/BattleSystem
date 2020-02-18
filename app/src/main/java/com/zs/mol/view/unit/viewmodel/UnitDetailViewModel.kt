package com.zs.mol.view.unit.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zs.mol.model.battle.unit.BaseUnit

class UnitDetailViewModel : ViewModel() {
    val unit: MutableLiveData<BaseUnit> = MutableLiveData()
}
