package com.zs.mol

import androidx.lifecycle.ViewModelProvider
import androidx.multidex.MultiDexApplication
import com.zs.mol.di.component.AppComponent
import com.zs.mol.di.component.DaggerAppComponent
import com.zs.mol.di.component.GameComponent
import com.zs.mol.model.notification.NotiManager


class MolApp : MultiDexApplication() {

    val component: AppComponent by lazy {
        DaggerAppComponent.factory().create(this)
    }

    val gameComponent: GameComponent by lazy {
        component.gameComponent().create()
    }

    override fun onCreate() {
        super.onCreate()
        initDagger()
        ViewModelProvider.AndroidViewModelFactory(this)
        startGame()
    }

    private fun initTest() {
        NotiManager.createChannels(applicationContext)
    }

    private fun initDagger() {
        component.apply {
            inject(this@MolApp)
        }
    }

    private fun startGame() {
    }
}
