package com.zs.mol.di.component

import com.zs.mol.MolApp
import com.zs.mol.di.module.AppModule
import com.zs.mol.di.module.GameModule
import com.zs.mol.di.scope.AppScope
import dagger.BindsInstance
import dagger.Component

@AppScope
@Component(modules = [AppModule::class, GameModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance app: MolApp): AppComponent
    }

    fun inject(app: MolApp)

    fun gameComponent(): GameComponent.Factory
}