package com.zs.mol

import androidx.multidex.MultiDexApplication
import com.zs.mol.model.unit.BaseUnitFactory
import com.zs.mol.model.user.UserManager


class App : MultiDexApplication() {
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