package com.zs.mol

import androidx.multidex.MultiDexApplication
import com.zs.mol.model.game.GameManager
import com.zs.mol.model.notification.NotiManager


class MolApp : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        initTest()
    }

    private fun initTest() {
        NotiManager.createChannels(applicationContext)
        GameManager.newGame(applicationContext)
    }
}