package com.zs.mol

import androidx.multidex.MultiDexApplication
import com.zs.mol.model.unit.BattleUnit
import com.zs.mol.model.user.UserManager


class App : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        initTest()
    }

    private fun initTest() {
        UserManager.user.units?.apply {
            add(BattleUnit(UserManager.getUserId()).apply {
                name = "iplepine"
            })
            add(BattleUnit(UserManager.getUserId()).apply {
                name = "Seoty"
            })
            add(BattleUnit(UserManager.getUserId()).apply {
                name = "PleleaseReleaseMe"
            })
        }
    }
}