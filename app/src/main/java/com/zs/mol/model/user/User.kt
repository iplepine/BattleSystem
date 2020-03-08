package com.zs.mol.model.user

import com.zs.mol.model.unit.BattleUnit

class User(val id: String) {
    @Volatile
    var userStatus = UserStatus(1, 0, 5000)
    val units = ArrayList<BattleUnit>()

    fun getUnit(id: String): BattleUnit? {
        return units.find { id == it.id }
    }

    fun addUnit(unit: BattleUnit) {
        units.add(unit)
    }

    fun toSaveData(): Map<String, Object> {
        return HashMap<String, Object>().apply {
            // TODO 이걸 해야 돼....
        }
    }
}
