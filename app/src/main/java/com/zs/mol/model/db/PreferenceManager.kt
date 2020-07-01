package com.zs.mol.model.db

import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.google.gson.Gson
import com.zs.mol.model.user.User
import com.zs.mol.model.user.UserData
import javax.inject.Inject

class PreferenceManager @Inject constructor(private val context: Context) {

    companion object {
        private const val PREF = "mol_pref"
        private const val USER_ID = "userId"
        private const val USER = "user"
    }

    private fun toJson(any: Any): String {
        return Gson().toJson(any)
    }

    fun getInt(key: String): Int {
        val preferences = context.getSharedPreferences(PREF, MODE_PRIVATE)
        return preferences.getInt(key, 0)
    }

    fun setInt(key: String, value: Int) {
        val preferences = context.getSharedPreferences(PREF, MODE_PRIVATE)
        preferences.edit().putInt(key, value).commit()
    }

    fun getString(key: String, default: String): String {
        val preferences = context.getSharedPreferences(PREF, MODE_PRIVATE)
        return preferences.getString(key, default)!!
    }

    fun setString(key: String, value: String) {
        val preferences = context.getSharedPreferences(PREF, MODE_PRIVATE)
        preferences.edit().putString(key, value).commit()
    }

    fun getUserId(): String? {
        val preferences = context.getSharedPreferences(USER, MODE_PRIVATE)
        return preferences.getString(USER_ID, null)
    }

    fun saveUserData(user: User) {
        val preferences = context.getSharedPreferences(USER, MODE_PRIVATE)
        preferences.edit()
            .putString(USER_ID, user.id)
            .putString(user.id, toJson(user.userData))
            .commit()
    }

    fun loadUserData(userId: String): UserData? {
        val preferences = context.getSharedPreferences(USER, MODE_PRIVATE)

        val json = preferences.getString(userId, "")

        return try {
            Gson().fromJson(json, UserData::class.java)
        } catch (e: Exception) {
            null
        }
    }
}