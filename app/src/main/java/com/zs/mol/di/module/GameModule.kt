package com.zs.mol.di.module

import android.content.Context
import com.zs.mol.di.component.UserComponent
import com.zs.mol.model.db.PreferenceManager
import dagger.Module
import dagger.Provides
import java.util.*
import javax.inject.Named

@Module(subcomponents = [UserComponent::class])
object GameModule {

    @Provides
    @JvmStatic
    @Named("lastUserId")
    fun provideLastUserId(context: Context): String {
        return PreferenceManager.getUserId(context) ?: UUID.randomUUID().toString()
    }
}
