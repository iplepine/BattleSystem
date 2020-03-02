package com.zs.mol.model

import android.content.Context
import com.zs.mol.model.db.PreferenceManager
import com.zs.mol.model.db.inventory.Inventory
import com.zs.mol.model.user.UserManager
import java.util.*

object GameManager {
    fun initGame(context: Context) {
        if (!loadGame(context)) {
            UserManager.initUser(UUID.randomUUID().toString())
            Inventory.initForNewUser()
        }
    }

    private fun loadGame(context: Context) : Boolean {
        val user = PreferenceManager.loadUser(context)
        return if (user == null) {
            false
        } else {
            UserManager.user = user
            val savedInventory = PreferenceManager.loadInventory(context, user.id)

            if (savedInventory == null) {
                Inventory.initForNewUser()
            } else {
                Inventory.putAll(savedInventory)
            }
            true
        }
    }

    fun saveGame(context: Context) {
        PreferenceManager.saveUser(context)
        PreferenceManager.saveInventory(context)
    }
}