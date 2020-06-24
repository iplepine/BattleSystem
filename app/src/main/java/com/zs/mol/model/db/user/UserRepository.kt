package com.zs.mol.model.db.user

import com.zs.mol.di.scope.AppScope
import com.zs.mol.model.user.User
import javax.inject.Inject
import javax.inject.Provider

@AppScope
class UserRepository @Inject constructor(
    private val local: UserLocalSource,
    private val remote: UserRemoteSource
) {

    fun getUser(id: String?): User? {
        return if (id == null) {
            local.getUser()
        } else {
            remote.getUser(id)
        }
    }
}

