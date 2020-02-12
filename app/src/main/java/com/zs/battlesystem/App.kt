package com.zs.battlesystem

import android.app.Application
import com.zs.battlesystem.model.battle.unit.BaseUnitFactory
import com.zs.battlesystem.model.user.User

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        initTest()
    }

    private fun initTest() {
        User.units.apply {
            add(BaseUnitFactory.create("Iplepine"))
            add(BaseUnitFactory.create("Seoty"))
            add(BaseUnitFactory.create("PleaseReleaseMe"))
        }
    }
}