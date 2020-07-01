package com.zs.mol.di.module

import com.zs.mol.di.component.QuestTabComponent
import com.zs.mol.di.component.UnitTabComponent
import com.zs.mol.di.scope.GameScope
import com.zs.mol.model.db.PreferenceManager
import com.zs.mol.model.db.user.UserRepository
import com.zs.mol.model.user.User
import dagger.Module
import dagger.Provides
import java.util.*
import javax.inject.Named

@Module(
    subcomponents = [
        QuestTabComponent::class,
        UnitTabComponent::class
    ]
)
object GameModule {

    @Provides
    @JvmStatic
    @Named("lastUserId")
    fun provideLastUserId(preferenceManager: PreferenceManager): String {
        return preferenceManager.getUserId() ?: UUID.randomUUID().toString()
    }

    @GameScope
    @Provides
    @JvmStatic
    fun provideUser(
        @Named("lastUserId")
        userId: String,
        userRepository: UserRepository
    ): User {
        return userRepository.getUser(userId)
    }
}
