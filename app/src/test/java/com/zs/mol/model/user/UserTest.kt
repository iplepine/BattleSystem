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

        val userJson = Gson().toJson(user)

        val parsedUser = UserManager.getUserGson().fromJson<User>(userJson, User::class.java)

        val newUserJson = Gson().toJson(parsedUser)

        assert(userJson == newUserJson)
    }
}