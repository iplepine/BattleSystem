package com.zs.mol.model.db.item

import androidx.lifecycle.LiveData
import com.zs.mol.di.scope.GameScope
import com.zs.mol.model.common.DefaultLiveData
import com.zs.mol.model.common.Logger
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject

@GameScope
class ItemRepository @Inject constructor() {
    private val db = ConcurrentHashMap<String, ConcurrentHashMap<String, Long>>()

    private val liveDataCache = HashMap<String, DefaultLiveData<Long>>()

    fun addItem(userId: String, id: String, amount: Long) {
        Logger.d("add item to inventory, itemId: $id, amount : $amount")

        var inventory = db[userId]
        if (inventory == null) {
            inventory = ConcurrentHashMap()
            db[userId] = inventory
        }

        val newAmount = getAmount(userId, id) + amount
        if (0 < newAmount) {
            inventory[id] = newAmount
            updateLiveData(id, newAmount)
        }

        Logger.d("current Inventory, itemId: $id, amount : ${getAmount(userId, id)}")
    }

    fun removeItem(userId: String, id: String, amount: Long) {
        Logger.d("remove item from inventory, itemId: $id, amount : $amount")

        val newAmount = (getAmount(userId, id) - amount).coerceAtLeast(0)

        val inventory = db[userId] ?: return
        if (inventory.containsKey(id)) {
            inventory[id] = newAmount
        }

        updateLiveData(id, newAmount)

        Logger.d("remove Inventory, itemId: $id, amount : ${getAmount(userId, id)}")
    }

    fun getAmount(userId: String, id: String): Long {
        return db[userId]?.get(id) ?: 0
    }

    fun getItemLiveData(userId: String, id: String): LiveData<Long> {
        return liveDataCache[id] ?: DefaultLiveData(getAmount(userId, id)).apply {
            liveDataCache[id] = this
        }
    }

    private fun updateLiveData(id: String, amount: Long) {
        liveDataCache[id]?.value = amount
    }
}