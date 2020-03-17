package com.zs.mol.model.unit

import com.zs.mol.model.action.UnitAction

open class UnitStatus {
    var level = 1
    var exp = 0L

    var name = "NoName"
    var race = Race.HUMAN
    var jobClass = JobClass.NOVICE

    var state: UnitState = UnitState.IDLE
    var action: UnitAction? = null

    fun toLevelName(): String {
        return "Lv. $level $name"
    }
}