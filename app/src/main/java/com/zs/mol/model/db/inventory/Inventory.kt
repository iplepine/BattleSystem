package com.zs.mol.model.db.inventory

import java.util.concurrent.ConcurrentHashMap
import kotlin.math.max

object Inventory : ConcurrentHashMap<String, Long>() {

    fun addItem(id: String, amount: Long) {
        if (0 < amount) {
            put(id, getAmount(id) + amount)
        }
    }

    fun removeItem(id: String, amount: Long) {
        if (contains(id)) {
            put(id, max(0, getAmount(id) - amount))
        }
    }

    fun getAmount(id: String): Long {
        return get(id)?: 0
    }
}
