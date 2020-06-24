package com.zs.mol.di.component

import com.zs.mol.di.module.UnitModule
import com.zs.mol.di.scope.GameScope
import dagger.Subcomponent

@GameScope
@Subcomponent(modules = [UnitModule::class])
interface QuestComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): QuestComponent
    }

}
