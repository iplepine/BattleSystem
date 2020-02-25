package com.zs.mol.model.user

import com.google.gson.Gson
import com.zs.mol.model.unit.BattleUnitFactory
import org.junit.Test

class UserTest {
    @Test
    fun toJsonTest() {
        val user = User("test_user")

        user.units.apply {
            add(BattleUnitFactory.createMyUnit("Iplepine"))
        }

        val json = user.toJson()
        println(json)

        val parsedUser = UserManager.getUserGson().fromJson<User>(json, User::class.java)

        val newUser = Gson().toJson(parsedUser)
        println(newUser.toString())
    }
}