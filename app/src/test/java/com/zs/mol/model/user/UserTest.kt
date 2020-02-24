package com.zs.mol.model.user

import com.zs.mol.model.unit.BattleUnitFactory
import org.junit.Test

class UserTest {
    @Test
    fun toJsonTest() {
        val user = User("test_user")

        user.units.apply {
            add(BattleUnitFactory.createMyUnit("Iplepine"))
            add(BattleUnitFactory.createMyUnit("Seoty"))
            add(BattleUnitFactory.createMyUnit("PleaseReleaseMe"))
        }

        println(user.toJson())
    }
}