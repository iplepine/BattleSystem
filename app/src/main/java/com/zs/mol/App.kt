package com.zs.mol

import androidx.multidex.MultiDexApplication
import com.zs.mol.model.GameManager
import com.zs.mol.model.unit.BattleUnit
import com.zs.mol.model.user.UserManager


class App : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        initTest()
    }

    private fun initTest() {
        GameManager.initGame(applicationContext)
    }
}