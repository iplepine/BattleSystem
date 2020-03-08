package com.zs.mol.model.user

import com.zs.mol.model.unit.BattleUnit

object UserManager {
    var user: User = User("init")

    fun initUser(userId: String) {
        user = User(userId)
    }

    fun getUserId(): String {
        return user.id
    }

    fun getEnemyId(): String {
        return "enemy"
    }

    fun getUnits(): List<BattleUnit> {
        return user.units
    }

    fun isMyUnit(id: String): Boolean {
        return user.id === id
    }

    fun getGold(): Long {
        return user.userStatus.gold
    }

    fun gainGold(amount: Long) {
        user.userStatus.gainGold(amount)
    }
}
