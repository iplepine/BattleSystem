package com.zs.mol

import androidx.multidex.MultiDexApplication
import com.zs.mol.model.game.GameEngine
import com.zs.mol.model.notification.NotiManager


class MolApp : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        initTest()
    }

    private fun initTest() {
        NotiManager.createChannels(applicationContext)
        GameEngine.newGame(applicationContext)
    }
}