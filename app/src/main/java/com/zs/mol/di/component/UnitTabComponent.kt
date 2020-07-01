package com.zs.mol.di.component

import com.zs.mol.di.module.UnitTabModule
import com.zs.mol.di.scope.MainTabScope
import com.zs.mol.view.quest.fragment.UnitHireFragment
import com.zs.mol.view.unit.fragment.UnitDetailFragment
import com.zs.mol.view.unit.fragment.UnitManageTabFragment
import dagger.Subcomponent

@MainTabScope
@Subcomponent(modules = [UnitTabModule::class])
interface UnitTabComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): UnitTabComponent
    }

    fun inject(fragment: UnitManageTabFragment)
    fun inject(fragment: UnitDetailFragment)

}
