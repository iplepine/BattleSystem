package com.zs.mol.di.module

import dagger.Module

@Module(includes = [AppModule::class])
class UserModule constructor(
    private val userId: String
) {
    /*@Provides
    fun provideUser(userRepository: UserRepository): User {
        return userRepository.getUser(userId)
    }*/
}