package com.zs.mol.model.db.inventory

import com.zs.mol.model.common.Logger
import com.zs.mol.model.item.ItemKey
import java.util.concurrent.ConcurrentHashMap
import kotlin.math.max

object Inventory : ConcurrentHashMap<String, Long>() {

    fun initForNewUser() {
        clear()
    }

    fun addItem(id: String, amount: Long) {
        Logger.d("add item to inventory, itemId: $id, amount : $amount")
        if (0 < amount) {
            put(id, getAmount(id) + amount)
        }
        Logger.d("current Inventory, itemId: $id, amount : ${getAmount(id)}")
    }

    fun removeItem(id: String, amount: Long) {
        Logger.d("remove item from inventory, itemId: $id, amount : $amount")
        if (containsKey(id)) {
            put(id, max(0, getAmount(id) - amount))
        }
        Logger.d("remove Inventory, itemId: $id, amount : ${getAmount(id)}")
    }

    fun getAmount(id: String): Long {
        return get(id) ?: 0
    }
}
