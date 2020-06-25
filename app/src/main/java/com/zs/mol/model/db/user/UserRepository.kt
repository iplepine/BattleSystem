package com.zs.mol.model.db.user

import com.zs.mol.di.scope.GameScope
import com.zs.mol.model.user.User
import javax.inject.Inject
import javax.inject.Provider

@GameScope
class UserRepository @Inject constructor(
    private val local: UserLocalSource,
    private val remote: UserRemoteSource
) {

    @Inject
    lateinit var userProvider: Provider<User>

    fun getUser(id: String): User {
        return local.getUser(id) ?: userProvider.get()
    }

    fun saveUser(user: User) {
        local.saveUser(user)
    }
}

