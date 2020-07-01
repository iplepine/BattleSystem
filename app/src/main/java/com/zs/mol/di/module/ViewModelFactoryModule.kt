package com.zs.mol.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zs.mol.view.ViewModelFactory
import com.zs.mol.view.ViewModelKey
import com.zs.mol.view.quest.viewmodel.QuestViewModel
import com.zs.mol.view.quest.viewmodel.UserStatusViewModel
import com.zs.mol.view.unit.viewmodel.UnitDetailViewModel
import com.zs.mol.view.unit.viewmodel.UnitViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelFactoryModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(QuestViewModel::class)
    abstract fun questViewModel(viewModel: QuestViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(UserStatusViewModel::class)
    abstract fun userStatusViewModel(viewModel: UserStatusViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(UnitViewModel::class)
    abstract fun unitViewModel(viewModel: UnitViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(UnitDetailViewModel::class)
    abstract fun unitDetailViewModel(viewModel: UnitDetailViewModel): ViewModel
}