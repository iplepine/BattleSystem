package com.zs.mol.di.component

import com.zs.mol.MainActivity
import com.zs.mol.di.module.GameModule
import com.zs.mol.di.module.UserModule
import com.zs.mol.di.scope.GameScope
import com.zs.mol.model.game.GameEngine
import com.zs.mol.model.quest.QuestRepository
import com.zs.mol.model.quest.factory.QuestFactory
import com.zs.mol.model.unit.BattleUnitFactory
import com.zs.mol.model.unit.UnitRepository
import com.zs.mol.view.base.MainFragment
import com.zs.mol.view.quest.fragment.NewQuestFragment
import com.zs.mol.view.quest.fragment.QuestFragment
import com.zs.mol.view.quest.fragment.UnitHireFragment
import com.zs.mol.view.unit.fragment.UnitDetailFragment
import com.zs.mol.view.unit.fragment.UnitManageFragment
import dagger.Subcomponent

@GameScope
@Subcomponent(modules = [GameModule::class, UserModule::class])
interface GameComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): GameComponent
    }

    fun inject(activity: MainActivity)
    fun inject(fragment: MainFragment)
    fun inject(fragment: UnitManageFragment)
    fun inject(fragment: UnitHireFragment)
    fun inject(fragment: UnitDetailFragment)
    fun inject(fragment: QuestFragment)
    fun inject(fragment: NewQuestFragment)

    fun userComponent(): UserComponent.Factory

    fun getLastUserId(): String
    fun gameEngine(): GameEngine
    fun unitRepository(): UnitRepository
    fun questRepository(): QuestRepository
    fun questFactory(): QuestFactory
    fun battleUnitFactory(): BattleUnitFactory
}
