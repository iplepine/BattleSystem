package com.zs.mol.model.unit

open class UnitStatus {
    var level = 1
    var exp = 0L

    var name = "NoName"
    var race = Race.HUMAN
    var jobClass = JobClass.NOVICE

    fun toLevelName(): String {
        return "Lv. $level $name"
    }
}