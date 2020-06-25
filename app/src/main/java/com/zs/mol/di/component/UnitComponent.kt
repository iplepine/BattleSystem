package com.zs.mol.di.component

import com.zs.mol.di.module.UnitModule
import com.zs.mol.di.scope.AfterLogin
import com.zs.mol.di.scope.GameScope
import dagger.Subcomponent

@AfterLogin
@Subcomponent(modules = [UnitModule::class])
interface UnitComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): UnitComponent
    }

}
