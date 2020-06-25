package com.zs.mol.di.component

import com.zs.mol.di.module.UnitModule
import com.zs.mol.di.scope.AfterLogin
import dagger.Subcomponent

@AfterLogin
@Subcomponent(modules = [UnitModule::class])
interface UnitComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): UnitComponent
    }
}
