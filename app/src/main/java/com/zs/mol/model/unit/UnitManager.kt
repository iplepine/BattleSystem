package com.zs.mol.model.unit

import com.zs.mol.model.user.UserManager

object UnitManager {
    val units = HashMap<String, BattleUnit>()

    fun getUnit(id: String): BattleUnit? {
        return UserManager.user.getUnit(id) ?: units[id]
    }

    fun removeUnit(id: String) {
        units.remove(id)
    }

    fun addUnit(unit: BattleUnit) {
        units.put(unit.id, unit)
    }
}
