package com.zs.mol.di.module

import android.content.Context
import com.zs.mol.MolApp
import com.zs.mol.di.component.GameComponent
import dagger.Module
import dagger.Provides

@Module(subcomponents = [GameComponent::class])
class AppModule {

    @Provides
    fun provideContext(app: MolApp): Context {
        return app.applicationContext
    }

}