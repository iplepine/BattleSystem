package com.zs.mol.model.db.inventory

import com.google.gson.Gson
import org.junit.Test

class InventoryTest {

    @Test
    fun jsonTest() {
        Inventory.addItem("gold", 777)
        val json = Gson().toJson(Inventory)

        Inventory.clear()

        val aa = Gson().fromJson(json, Inventory::class.java)
        assert(Inventory["gold"] == null)
        Inventory.putAll(aa)
        assert(Inventory["gold"] == 777L)
    }
}