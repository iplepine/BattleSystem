package com.zs.mol.model.db.user

import com.zs.mol.model.user.User
import javax.inject.Inject

class UserRemoteSource @Inject constructor() {
    fun getUser(id: String): User? {
        return null
    }

    fun saveUser(user: User) {

    }
}