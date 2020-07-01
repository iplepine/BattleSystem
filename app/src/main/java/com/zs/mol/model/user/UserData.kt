package com.zs.mol.model.user

import com.zs.mol.model.unit.BattleUnit

class UserData(var level: Int = 1, var exp: Long = 0) {

    val units = ArrayList<BattleUnit>()

    fun addExp(amount: Long) {
        exp += amount
    }

    fun reduceExp(amount: Long) {
        exp -= amount
    }
}