package com.zs.mol.di.component

import com.zs.mol.di.module.DungeonModule
import com.zs.mol.di.scope.AfterLogin
import dagger.Subcomponent

@AfterLogin
@Subcomponent(modules = [DungeonModule::class])
interface DungeonComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): DungeonComponent
    }
}
