package com.zs.mol.model.user

import com.zs.mol.model.db.inventory.Inventory
import com.zs.mol.model.unit.BattleUnit

class User(val id: String) {
    @Volatile
    var userStatus = UserStatus(1, 0, 0)
    val units = ArrayList<BattleUnit>()

    lateinit var inventory: Inventory

    fun getUnit(id: String): BattleUnit? {
        return units.find { id == it.id }
    }

    fun addUnit(unit: BattleUnit) {
        units.add(unit)
    }

    fun addItem(id: String, amount: Long) {
        inventory.addItem(id, amount)
    }

    fun useItem(id: String, amount: Long) {
        inventory.removeItem(id, amount)
    }

    fun toSaveData(): Map<String, Object> {
        return HashMap<String, Object>().apply {
            // TODO 이걸 해야 돼....
        }
    }
}
