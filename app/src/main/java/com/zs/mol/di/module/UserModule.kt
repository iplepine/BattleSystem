package com.zs.mol.di.module

import com.zs.mol.model.db.user.UserRepository
import com.zs.mol.model.user.User
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
object UserModule {

    @Provides
    fun provideUser(userRepository: UserRepository, @Named("lastUserId") lastUserId: String): User {
        return userRepository.getUser(lastUserId)
    }

}