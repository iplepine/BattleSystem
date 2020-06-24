package com.zs.mol.model.db.item

import com.zs.mol.di.scope.GameScope
import com.zs.mol.model.common.Logger
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject
import kotlin.math.max

@GameScope
class ItemRepository @Inject constructor() {
    private val db = ConcurrentHashMap<String, ConcurrentHashMap<String, Long>>()

    fun addItem(userId: String, id: String, amount: Long) {
        Logger.d("add item to inventory, itemId: $id, amount : $amount")

        var inventory = db[userId]
        if (inventory == null) {
            inventory = ConcurrentHashMap()
            db[userId] = inventory
        }
        if (0 < amount) {
            inventory[id] = getAmount(userId, id) + amount
        }

        Logger.d("current Inventory, itemId: $id, amount : ${getAmount(userId, id)}")
    }

    fun removeItem(userId: String, id: String, amount: Long) {
        Logger.d("remove item from inventory, itemId: $id, amount : $amount")

        val inventory = db[userId] ?: return
        if (inventory.containsKey(id)) {
            inventory[id] = max(0, getAmount(userId, id) - amount)
        }

        Logger.d("remove Inventory, itemId: $id, amount : ${getAmount(userId, id)}")
    }

    fun setItem(userId: String, id: String, amount: Long) {
        var inventory = db[userId]
        if (inventory == null) {
            inventory = ConcurrentHashMap()
            db[userId] = inventory
        }
        if (0 < amount) {
            inventory[id] = amount
        }
    }

    fun getAmount(userId: String, id: String): Long {
        return db[userId]?.get(id) ?: 0
    }
}