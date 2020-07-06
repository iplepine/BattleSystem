package com.zs.mol.di.component

import com.zs.mol.MainActivity
import com.zs.mol.di.module.*
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
import dagger.Subcomponent

@GameScope
@Subcomponent(
    modules = [
        GameModule::class,
        UnitTabModule::class,
        QuestTabModule::class,
        ViewModelFactoryModule::class,
        DatabaseModule::class
    ]
)
interface GameComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): GameComponent
    }

    fun inject(activity: MainActivity)
    fun inject(fragment: BaseFragment)
    fun inject(fragment: BaseDialogFragment)
    fun inject(fragment: MainTabFragment)

    fun unitTabComponent(): UnitTabComponent.Factory
    fun questTabComponent(): QuestTabComponent.Factory

    fun gameEngine(): GameEngine
    fun userRepository(): UserRepository
    fun unitRepository(): UnitRepository
    fun questRepository(): QuestRepository
    fun questFactory(): QuestFactory
    fun battleUnitFactory(): BattleUnitFactory
}
