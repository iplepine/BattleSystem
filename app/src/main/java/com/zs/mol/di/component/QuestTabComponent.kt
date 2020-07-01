package com.zs.mol.di.component

import com.zs.mol.di.module.UnitTabModule
import com.zs.mol.di.scope.MainTabScope
import com.zs.mol.view.quest.fragment.NewQuestFragment
import com.zs.mol.view.quest.fragment.QuestTabFragment
import com.zs.mol.view.quest.fragment.UnitHireFragment
import dagger.Subcomponent

@MainTabScope
@Subcomponent(modules = [UnitTabModule::class])
interface QuestTabComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): QuestTabComponent
    }

    fun inject(fragment: QuestTabFragment)
    fun inject(fragment: NewQuestFragment)
    fun inject(fragment: UnitHireFragment)
}
