package com.zs.mol.di.component

import com.zs.mol.MainActivity
import com.zs.mol.di.module.MainActivityModule
import com.zs.mol.di.scope.ActivityScope
import com.zs.mol.view.base.MainFragment
import com.zs.mol.view.quest.fragment.NewQuestFragment
import com.zs.mol.view.quest.fragment.QuestFragment
import com.zs.mol.view.quest.fragment.UnitHireFragment
import com.zs.mol.view.unit.fragment.UnitDetailFragment
import com.zs.mol.view.unit.fragment.UnitManageFragment
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = [MainActivityModule::class])
interface MainActivityComponent {
    fun inject(activity: MainActivity)
    fun inject(fragment: MainFragment)
    fun inject(fragment: UnitManageFragment)
    fun inject(fragment: UnitHireFragment)
    fun inject(fragment: UnitDetailFragment)
    fun inject(fragment: QuestFragment)
    fun inject(fragment: NewQuestFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(): MainActivityComponent
    }
}