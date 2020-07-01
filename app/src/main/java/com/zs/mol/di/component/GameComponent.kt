package com.zs.mol.di.component

import com.zs.mol.MainActivity
import com.zs.mol.di.module.GameModule
import com.zs.mol.di.module.QuestTabModule
import com.zs.mol.di.module.UnitTabModule
import com.zs.mol.di.module.ViewModelFactoryModule
import com.zs.mol.di.scope.GameScope
import com.zs.mol.model.db.user.UserRepository
import com.zs.mol.model.game.GameEngine
import com.zs.mol.model.quest.QuestRepository
import com.zs.mol.model.quest.factory.QuestFactory
import com.zs.mol.model.unit.BattleUnitFactory
import com.zs.mol.model.unit.UnitRepository
import com.zs.mol.model.user.User
import com.zs.mol.view.base.BaseDialogFragment
import com.zs.mol.view.base.BaseFragment
import com.zs.mol.view.base.MainTabFragment
import dagger.Component

@GameScope
@Component(
    dependencies = [AppComponent::class],
    modules = [
        GameModule::class,
        UnitTabModule::class,
        QuestTabModule::class,
        ViewModelFactoryModule::class
    ]
)

interface GameComponent {

    @Component.Factory
    interface Factory {
        fun create(appComponent: AppComponent): GameComponent
    }

    fun inject(activity: MainActivity)
    fun inject(fragment: BaseFragment)
    fun inject(fragment: BaseDialogFragment)
    fun inject(fragment: MainTabFragment)

    fun unitTabComponent(): UnitTabComponent.Factory
    fun questTabComponent(): QuestTabComponent.Factory

    fun gameEngine(): GameEngine
    fun user(): User
    fun userRepository(): UserRepository
    fun unitRepository(): UnitRepository
    fun questRepository(): QuestRepository
    fun questFactory(): QuestFactory
    fun battleUnitFactory(): BattleUnitFactory
}
