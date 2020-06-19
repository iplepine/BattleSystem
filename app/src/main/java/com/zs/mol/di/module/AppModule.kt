package com.zs.mol.di.module

import android.content.Context
import com.zs.mol.MolApp
import com.zs.mol.di.component.MainActivityComponent
import dagger.Module
import dagger.Provides

@Module(subcomponents = [MainActivityComponent::class])
class AppModule(val molApp: MolApp) {

    @Provides
    fun provideApp(): MolApp {
        return molApp
    }

    @Provides
    fun provideContext(): Context {
        return molApp.applicationContext
    }

}