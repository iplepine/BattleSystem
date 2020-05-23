package com.zs.mol.model.unit

import androidx.lifecycle.MutableLiveData
import com.zs.mol.model.unit.action.UnitAction
import com.zs.mol.model.unit.action.WaitingAction

open class UnitStatus {
    val levelLiveData = MutableLiveData<Int>().apply {
        value = 1
    }
    var level = 1
        set(value) {
            field = value
            levelLiveData.value = value
        }

    var exp = 0L

    val nameLiveData = MutableLiveData<String>().apply {
        value = "NoName"
    }
    var name = "NoName"
        set(value) {
            field = value
            nameLiveData.value = value
        }

    var faceImage = ""

    var race = Race.HUMAN
    var jobClass = JobClass.NOVICE

    var state: UnitState = UnitState.IDLE
    var action: UnitAction = WaitingAction(5000)

    fun toLevelName(): String {
        return "Lv. $level $name"
    }
}