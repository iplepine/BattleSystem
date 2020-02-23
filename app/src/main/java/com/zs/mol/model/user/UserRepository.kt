package com.zs.mol.model.user

import com.zs.mol.model.unit.BattleUnit

object UserRepository {
    var user = User("guest")

    fun newUser(id: String) {
        user = User(id)
    }

    fun getUserId(): String {
        return user.id ?: "guest"
    }

    fun getUnits(): List<BattleUnit> {
        return user.units ?: emptyList()
    }

    fun addUnit(unit: BattleUnit) {
        user.units?.add(unit)
    }

    fun isMyUnit(id: String): Boolean {
        return user.id === id
    }
}
