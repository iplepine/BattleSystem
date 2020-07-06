package com.zs.mol.di.component

import com.zs.mol.MolApp
import com.zs.mol.di.module.AppModule
import com.zs.mol.di.module.GameModule
import com.zs.mol.model.db.PreferenceManager
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        GameModule::class
    ]
)
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance app: MolApp): AppComponent
    }

    fun gameComponent(): GameComponent.Factory

    fun inject(app: MolApp)

    fun preferenceManager(): PreferenceManager
}