package com.zs.mol.model.user

import com.google.gson.Gson
import com.zs.mol.model.unit.BattleUnit
import com.zs.mol.model.unit.BattleUnitFactory


object UserManager {
    var user = User("guest")

    fun newUser(id: String) {
        user = User(id)
    }

    fun getUserId(): String {
        return user.id ?: "guest"
    }

    fun getEnemyId(): String {
        return "enemy"
    }

    fun getUnits(): List<BattleUnit> {
        return user.units ?: emptyList()
    }

    fun addUnit(unit: BattleUnit) {
        unit.owner = getUserId()
        user.units?.add(unit)
    }

    fun addExp(exp: Long) {
        user.userStatus.exp += exp
    }

    fun isMyUnit(id: String): Boolean {
        return user.id === id
    }

    fun getUserGson(): Gson {
        return Gson()
    }

    fun loadData() {
        user.units?.apply {
            add(BattleUnitFactory.createMyUnit("Iplepine"))
            add(BattleUnitFactory.createMyUnit("Seoty"))
            add(BattleUnitFactory.createMyUnit("PleaseReleaseMe"))
        }
    }
}
