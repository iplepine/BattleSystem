package com.zs.mol.di.component

import com.zs.mol.di.module.QuestModule
import com.zs.mol.di.scope.AfterLogin
import com.zs.mol.di.scope.GameScope
import dagger.Subcomponent

@AfterLogin
@Subcomponent(modules = [QuestModule::class])
interface QuestComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): QuestComponent
    }

}
