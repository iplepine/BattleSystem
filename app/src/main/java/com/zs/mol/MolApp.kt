package com.zs.mol

import androidx.lifecycle.ViewModelProvider
import androidx.multidex.MultiDexApplication
import com.zs.mol.di.component.AppComponent
import com.zs.mol.di.component.DaggerAppComponent
import com.zs.mol.di.module.AppModule
import com.zs.mol.model.db.user.UserRepository
import com.zs.mol.model.game.GameEngine
import com.zs.mol.model.notification.NotiManager
import javax.inject.Inject


class MolApp : MultiDexApplication() {

    @Inject
    lateinit var engine: GameEngine

    @Inject
    lateinit var repo: UserRepository

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        initDagger()
        ViewModelProvider.AndroidViewModelFactory(this)
        initTest()
    }

    private fun initTest() {
        NotiManager.createChannels(applicationContext)
        engine.newGame(applicationContext)
    }

    private fun initDagger() {
        appComponent.apply {
            inject(this@MolApp)
        }
    }
}
