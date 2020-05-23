package com.zs.mol

import androidx.lifecycle.ViewModelProvider
import androidx.multidex.MultiDexApplication
import com.zs.mol.model.game.GameEngine
import com.zs.mol.model.notification.NotiManager


class MolApp : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        ViewModelProvider.AndroidViewModelFactory(this)
        initTest()
    }

    private fun initTest() {
        NotiManager.createChannels(applicationContext)
        GameEngine.newGame(applicationContext)
    }
}