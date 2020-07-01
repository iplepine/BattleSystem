package com.zs.mol.model.db.user

import com.zs.mol.model.user.User
import com.zs.mol.model.user.UserData
import javax.inject.Inject

class UserRemoteSource @Inject constructor() {
    fun getUser(id: String): UserData? {
        return null
    }

    fun saveUser(user: User) {

    }
}