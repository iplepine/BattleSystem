package com.zs.mol.model.db.inventory

import com.zs.mol.model.item.ItemKey
import java.util.concurrent.ConcurrentHashMap
import kotlin.math.max

object Inventory : ConcurrentHashMap<String, Long>() {

    fun initForNewUser() {
        clear()
        put(ItemKey.GOLD, 1000)
    }

    fun addItem(id: String, amount: Long) {
        if (0 < amount) {
            put(id, getAmount(id) + amount)
        }
    }

    fun removeItem(id: String, amount: Long) {
        if (containsKey(id)) {
            put(id, max(0, getAmount(id) - amount))
        }
    }

    fun getAmount(id: String): Long {
        return get(id)?: 0
    }
}
