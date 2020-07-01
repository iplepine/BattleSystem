package com.zs.mol.model.unit

import com.zs.mol.di.scope.GameScope
import com.zs.mol.model.user.User
import javax.inject.Inject

@GameScope
class UnitRepository @Inject constructor() {
    val units = HashMap<String, BattleUnit>()

    fun getUnit(user: User, id: String): BattleUnit? {
        return user.getUnit(id) ?: units[id]
    }

    fun removeUnit(id: String) {
        units.remove(id)
    }

    fun addUnit(unit: BattleUnit) {
        units.put(unit.id, unit)
    }
}
