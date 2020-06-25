package com.zs.mol.model.db.user

import com.zs.mol.model.db.PreferenceManager
import com.zs.mol.model.user.User
import org.junit.Assert.*

class UserLocalSourceTest {

    class TestLocalSource : UserLocalSource() {
        override fun getUser(userId: String): User? {
            return User("test")
        }
    }
}