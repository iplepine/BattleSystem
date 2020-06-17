package com.zs.mol.di.module

import com.zs.mol.model.db.user.UserRepository
import com.zs.mol.model.user.User
import dagger.Module
import dagger.Provides

@Module(includes = [AppModule::class])
class UserModule constructor(
    private val userId: String
) {
    @Provides
    fun provideUser(userRepository: UserRepository): User {
        return userRepository.getUser(userId)
    }
}