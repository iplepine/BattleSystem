package com.zs.mol.di.module

import com.zs.mol.model.unit.BattleUnitFactory
import dagger.Module
import dagger.Provides

@Module
object UnitModule {

    @Provides
    fun provideRandomName(battleUnitFactory: BattleUnitFactory): String {
        return battleUnitFactory.getRandomName()
    }

}