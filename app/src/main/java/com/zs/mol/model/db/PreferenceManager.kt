package com.zs.mol.model.db

import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.google.gson.Gson
import com.zs.mol.model.db.inventory.Inventory
import com.zs.mol.model.user.User
import com.zs.mol.model.user.UserManager

object PreferenceManager {

    private const val USER = "user"
    private const val INVENTORY = "inventory"

    private const val USER_ID = "userId"

    private fun toJson(any: Any): String {
        return Gson().toJson(any)
    }

    fun saveUser(context: Context) {
        val preferences = context.getSharedPreferences(USER, MODE_PRIVATE)
        var user = UserManager.user
        preferences.edit()
            .putString(USER_ID, user.id)
            .putString(user.id, toJson(user))
            .commit()
    }

    fun loadUser(context: Context): User? {
        val preferences = context.getSharedPreferences(USER, MODE_PRIVATE)

        val userId = preferences.getString(USER_ID, "")
        val json = preferences.getString(userId, "")

        return try {
            Gson().fromJson(json, User::class.java)
        } catch (e: Exception) {
            null
        }
    }

    fun saveInventory(context: Context) {
        val preferences = context.getSharedPreferences(INVENTORY, MODE_PRIVATE)
        val userId = UserManager.getUserId()
        preferences.edit().putString(userId, toJson(Inventory)).commit()
    }

    fun loadInventory(context: Context, userId: String): Inventory? {
        val preferences = context.getSharedPreferences(INVENTORY, MODE_PRIVATE)
        val json = preferences.getString(userId, "")

        return try {
            Gson().fromJson(json, Inventory::class.java)
        } catch (e: Exception) {
            null
        }
    }
}