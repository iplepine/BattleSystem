package com.zs.mol.di.module

import android.content.Context
import com.zs.mol.MolApp
import com.zs.mol.di.component.GameComponent
import com.zs.mol.di.scope.AppScope
import dagger.Module
import dagger.Provides

@Module(subcomponents = [GameComponent::class])
class AppModule {

    @AppScope
    @Provides
    fun provideContext(app: MolApp): Context {
        return app.applicationContext
    }

}