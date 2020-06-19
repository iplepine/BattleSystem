package com.zs.mol.di.component

import com.zs.mol.MolApp
import com.zs.mol.di.module.AppModule
import com.zs.mol.di.module.MainActivityModule
import com.zs.mol.model.db.user.UserRepository
import com.zs.mol.model.game.GameEngine
import com.zs.mol.model.unit.action.UnitActionManager
import com.zs.mol.model.user.UserManager
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, MainActivityModule::class])
interface AppComponent {
    fun inject(app: MolApp)

    fun userRepository(): UserRepository
    fun userManager(): UserManager
    fun gameEngine(): GameEngine
    fun unitActionManager(): UnitActionManager

    fun mainActivityComponent(): MainActivityComponent.Factory
}