package com.zs.mol.model.db.user

import com.zs.mol.model.consts.ReservedUserId
import com.zs.mol.model.db.PreferenceManager
import com.zs.mol.model.user.User
import com.zs.mol.model.user.UserData
import javax.inject.Inject
import javax.inject.Provider

class UserLocalSource @Inject constructor(private val preferenceManager: PreferenceManager) {

    fun getGuestId(): String {
        return ReservedUserId.GUEST
    }

    fun load(userId: String): UserData? {
        return preferenceManager.loadUserData(userId)
    }

    fun save(user: User) {
        preferenceManager.saveUserData(user)
    }
}