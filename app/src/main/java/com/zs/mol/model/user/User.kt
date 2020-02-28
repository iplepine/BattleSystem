package com.zs.mol.model.user

import com.zs.mol.model.unit.BattleUnit

class User(val id: String) {
    companion object {
        fun fromSaveData(data: String): User? {
            return try {
                UserManager.getUserGson().fromJson<User>(data, User.javaClass)
            } catch (e: Exception) {
                null
            }
        }
    }

    var userStatus = UserStatus(1)
    val units = ArrayList<BattleUnit>()

    fun getUnit(id: String): BattleUnit? {
        return units.find { id == it.id }
    }

    fun toSaveData(): Map<String, Object> {
        return HashMap<String, Object>().apply {
            // TODO 이걸 해야 돼....
        }
    }

    fun toJson(): String {
        return UserManager.getUserGson().toJson(this)
    }
}
