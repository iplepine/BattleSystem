package com.zs.mol

import androidx.multidex.MultiDexApplication
import com.zs.mol.model.unit.BaseUnitFactory
import com.zs.mol.model.unit.BattleUnit
import com.zs.mol.model.user.UserRepository


class App : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        initTest()
    }

    private fun initTest() {
        UserRepository.user.units?.apply {
            add(BattleUnit(BaseUnitFactory.create("Iplepine")))
            add(BattleUnit(BaseUnitFactory.create("Seoty")))
            add(BattleUnit(BaseUnitFactory.create("PleaseReleaseMe")))
        }
    }
}