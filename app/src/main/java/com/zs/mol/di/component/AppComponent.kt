package com.zs.mol.di.component

import com.zs.mol.MolApp
import com.zs.mol.di.module.AppModule
import com.zs.mol.view.base.BaseFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(app: MolApp)

    fun inject(fragment : BaseFragment)
}