package com.zs.mol.model.db.inventory

import com.google.gson.Gson
import com.zs.mol.model.user.User
import org.junit.Test

class InventoryTest {

    @Test
    fun jsonTest() {
        val user = User("guest")
        user.inventory.addItem("testItem", 777)
        val json = Gson().toJson(user.inventory)

        user.inventory.clear()

        val aa = Gson().fromJson(json, Inventory::class.java)
        assert(user.inventory["testItem"] == null)
        user.inventory.putAll(aa)
        assert(user.inventory["testItem"] == 777L)
    }
}