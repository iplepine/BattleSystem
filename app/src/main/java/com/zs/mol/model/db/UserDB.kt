package com.zs.mol.model.db

import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.zs.mol.model.user.User

object UserDB {

    const val USER = "user"

    fun saveUser(context: Context, user:User) {
        val preferences = context.getSharedPreferences(USER, MODE_PRIVATE)
        preferences.edit().putString(USER, user.toJson()).commit()
    }

    fun loadUser(context: Context): User? {
        val preferences = context.getSharedPreferences(USER, MODE_PRIVATE)
        val json = preferences.getString(USER, "")
        return User.fromSaveData(json)
    }
}