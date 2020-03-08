package com.zs.mol.model

import android.content.Context
import com.zs.mol.model.common.Logger
import com.zs.mol.model.db.PreferenceManager
import com.zs.mol.model.db.inventory.Inventory
import com.zs.mol.model.user.UserManager
import java.util.*

object GameManager {
    fun newGame(context: Context) {
        if (!loadGame(context)) {
            UserManager.initUser(UUID.randomUUID().toString())

            Logger.d("start new user")
        }
    }

    private fun loadGame(context: Context): Boolean {
        val user = PreferenceManager.loadUser(context)
        return if (user == null) {
            false
        } else {
            UserManager.user = user
            val inventory = PreferenceManager.loadInventory(context, user.id)

            if (inventory == null) {
                Inventory.initForNewUser()
            } else {
                Inventory.putAll(inventory)
            }

            Logger.d("load previous user")
            true
        }
    }

    fun saveGame(context: Context) {
        PreferenceManager.saveUser(context)
        PreferenceManager.saveInventory(context)

        Logger.d("save the game")
    }
}