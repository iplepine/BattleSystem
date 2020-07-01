package com.zs.mol.di.module

import android.content.Context
import com.zs.mol.MolApp
import dagger.Module
import dagger.Provides

@Module
class AppModule {

    @Provides
    fun provideContext(app: MolApp): Context {
        return app.applicationContext
    }

}