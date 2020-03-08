package com.zs.mol

import androidx.multidex.MultiDexApplication
import com.zs.mol.model.GameManager


class App : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        initTest()
    }

    private fun initTest() {
        GameManager.newGame(applicationContext)
    }
}