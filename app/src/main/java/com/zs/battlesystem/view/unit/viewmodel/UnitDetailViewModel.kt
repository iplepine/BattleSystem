package com.zs.battlesystem.view.unit.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zs.battlesystem.model.battle.unit.BaseUnit

class UnitDetailViewModel : ViewModel() {
    val unit: MutableLiveData<BaseUnit> = MutableLiveData()
}
