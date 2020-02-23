package com.zs.mol.model.user

import com.google.gson.Gson
import com.zs.mol.model.unit.BattleUnit

class User(val id: String) {
    companion object {
        fun fromSaveData(data: String): User? {
            return try {
                Gson().fromJson<User>(data, User.javaClass)
            } catch (e: Exception) {
                null
            }
        }
    }

    var userStatus = UserStatus(1, 0)
    val units = ArrayList<BattleUnit>()

    fun toSaveData(): Map<String, Object> {
        return HashMap()
    }

    fun toJson(): String {
        return Gson().toJson(this)
    }
}
