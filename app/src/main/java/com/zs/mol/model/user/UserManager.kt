package com.zs.mol.model.user

import android.content.Context
import com.google.gson.Gson
import com.zs.mol.model.db.UserDB
import com.zs.mol.model.unit.BattleUnit
import com.zs.mol.model.unit.BattleUnitFactory
import java.util.*

object UserManager {
    var user: User = User("init")

    fun initUser(context: Context) {
        user = UserDB.loadUser(context)?: newUser(UUID.randomUUID().toString())
    }

    fun newUser(id: String): User {
        return User(id)
    }

    fun getUserId(): String {
        return user.id
    }

    fun getEnemyId(): String {
        return "enemy"
    }

    fun getUnits(): List<BattleUnit> {
        return user.units ?: emptyList()
    }

    fun addUnit(unit: BattleUnit) {
        unit.owner = getUserId()
        user.units?.add(unit)
    }

    fun addExp(exp: Long) {
        user.userStatus.exp += exp
    }

    fun isMyUnit(id: String): Boolean {
        return user.id === id
    }

    fun getUserGson(): Gson {
        return Gson()
    }
}
