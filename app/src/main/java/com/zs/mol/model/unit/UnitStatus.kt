package com.zs.mol.model.unit

import com.zs.mol.model.unit.action.UnitAction
import com.zs.mol.model.unit.action.WaitingAction

open class UnitStatus {
    var level = 1
    var exp = 0L

    var name = "NoName"
    var race = Race.HUMAN
    var jobClass = JobClass.NOVICE

    var state: UnitState = UnitState.IDLE
    var action: UnitAction = WaitingAction(5000)

    fun toLevelName(): String {
        return "Lv. $level $name"
    }
}