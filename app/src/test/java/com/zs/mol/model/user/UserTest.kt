package com.zs.mol.model.user

import com.zs.mol.model.unit.BaseUnitFactory
import org.junit.Test

class UserTest {
    @Test
    fun toJsonTest() {
        val user = User("test_user")

        user.units.apply {
            add(BaseUnitFactory.create("Iplepine"))
            add(BaseUnitFactory.create("Seoty"))
            add(BaseUnitFactory.create("PleaseReleaseMe"))
        }

        println(user.toJson())
    }
}