package com.zs.mol.model.user

import com.zs.mol.model.unit.BaseUnitFactory
import com.zs.mol.model.unit.BattleUnit
import org.junit.Test

class UserTest {
    @Test
    fun toJsonTest() {
        val user = User("test_user")

        user.units.apply {
            add(BattleUnit(BaseUnitFactory.create("Iplepine")))
            add(BattleUnit(BaseUnitFactory.create("Seoty")))
            add(BattleUnit(BaseUnitFactory.create("PleaseReleaseMe")))
        }

        println(user.toJson())
    }
}