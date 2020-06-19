package com.zs.mol.model.db

import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.google.gson.Gson
import com.zs.mol.model.db.inventory.Inventory
import com.zs.mol.model.user.User

object PreferenceManager {

    private const val PREF = "mol_pref"
    private const val USER = "user"
    private const val INVENTORY = "inventory"

    private const val USER_ID = "userId"

    private fun toJson(any: Any): String {
        return Gson().toJson(any)
    }

    fun getInt(context: Context, key: String): Int {
        val preferences = context.getSharedPreferences(PREF, MODE_PRIVATE)
        return preferences.getInt(key, 0)
    }

    fun setInt(context: Context, key: String, value: Int) {
        val preferences = context.getSharedPreferences(PREF, MODE_PRIVATE)
        preferences.edit().putInt(key, value).commit()
    }

    fun getString(context: Context, key: String, value: String): String {
        val preferences = context.getSharedPreferences(PREF, MODE_PRIVATE)
        return preferences.getString(key, "")
    }

    fun setString(context: Context, key: String, value: String) {
        val preferences = context.getSharedPreferences(PREF, MODE_PRIVATE)
        preferences.edit().putString(key, value).commit()
    }

    fun saveUser(context: Context, user: User) {
        val preferences = context.getSharedPreferences(USER, MODE_PRIVATE)
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

    fun saveInventory(context: Context, user: User) {
        val preferences = context.getSharedPreferences(INVENTORY, MODE_PRIVATE)
        val userId = user.id
        val inventory = user.inventory
        preferences.edit().putString(userId, toJson(inventory)).commit()
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