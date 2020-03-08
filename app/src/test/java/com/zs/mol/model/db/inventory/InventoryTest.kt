package com.zs.mol.model.db.inventory

import com.google.gson.Gson
import org.junit.Test

class InventoryTest {

    @Test
    fun jsonTest() {
        Inventory.addItem("testItem", 777)
        val json = Gson().toJson(Inventory)

        Inventory.clear()

        val aa = Gson().fromJson(json, Inventory::class.java)
        assert(Inventory["testItem"] == null)
        Inventory.putAll(aa)
        assert(Inventory["testItem"] == 777L)
    }
}