package com.zs.mol.di.module

import android.content.Context
import com.zs.mol.di.component.UserComponent
import com.zs.mol.model.consts.ReservedUserId
import com.zs.mol.model.db.PreferenceManager
import dagger.Module
import dagger.Provides

@Module(subcomponents = [UserComponent::class])
object GameModule {

    @Provides
    @JvmStatic
    fun provideLastUserId(context: Context): String {
        return PreferenceManager.getUserId(context) ?: ReservedUserId.GUEST
    }
}
