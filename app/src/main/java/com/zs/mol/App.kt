package com.zs.mol

import android.app.Application
import com.zs.mol.model.battle.unit.BaseUnitFactory
import com.zs.mol.model.user.UserManager


class App : Application() {
    override fun onCreate() {
        super.onCreate()
        initTest()
    }

    private fun initTest() {
        UserManager.user.value?.units?.apply {
            add(BaseUnitFactory.create("Iplepine"))
            add(BaseUnitFactory.create("Seoty"))
            add(BaseUnitFactory.create("PleaseReleaseMe"))
        }
    }
}