package com.zs.mol.di.module

import dagger.Module

@Module
class UserModule constructor(
    private val userId: String
) {
}