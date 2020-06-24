package com.zs.mol.di.component

import com.zs.mol.di.module.UserModule
import com.zs.mol.di.scope.AfterLogin
import dagger.BindsInstance
import dagger.Subcomponent

@AfterLogin
@Subcomponent(modules = [UserModule::class])
interface UserComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance userId: String): UserComponent
    }

}
