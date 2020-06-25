package com.zs.mol.model.db.user

import android.content.Context
import com.zs.mol.model.db.PreferenceManager
import com.zs.mol.model.user.User
import javax.inject.Inject

class UserLocalSource @Inject constructor(private val context: Context) {

    fun getUser(userId: String): User? {
        return PreferenceManager.loadUser(context, userId)
    }

    fun saveUser(user: User) {
        PreferenceManager.saveUser(context, user)
    }
}