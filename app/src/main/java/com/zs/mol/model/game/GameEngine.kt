package com.zs.mol.model.game

import android.content.Context
import com.zs.mol.model.common.Logger
import com.zs.mol.model.db.PreferenceManager
import com.zs.mol.model.db.inventory.Inventory
import com.zs.mol.model.unit.action.UnitActionManager
import com.zs.mol.model.user.UserManager
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import java.util.*
import java.util.concurrent.TimeUnit

object GameEngine {
    private const val TIME_PERIOD = 100L

    val timeSubject: PublishSubject<Long> = PublishSubject.create<Long>()
    private var disposable: Disposable? = null

    fun newGame(context: Context) {
        if (!loadGame(context)) {
            UserManager.initUser(UUID.randomUUID().toString())

            Logger.d("start new user")
        }
    }

    private fun loadGame(context: Context): Boolean {
        val user = PreferenceManager.loadUser(context)
        return if (user == null) {
            false
        } else {
            UserManager.user = user
            var inventory = PreferenceManager.loadInventory(context, user.id)
            if (inventory == null) {
                inventory = Inventory()
            } else {
                inventory.putAll(inventory)
            }
            UserManager.user.inventory = inventory

            Logger.d("load previous user")
            true
        }
    }

    fun saveGame(context: Context) {
        PreferenceManager.saveInventory(context)

        Logger.d("save the game")
    }

    fun onResume() {
        Logger.d("Start game engine")

        val timer = Observable.interval(TIME_PERIOD, TimeUnit.MILLISECONDS)
        disposable = timer.subscribe(
            {
                updateTime(TIME_PERIOD)
            },
            { Logger.e(it.toString()) },
            {
                disposable?.dispose()
                disposable = null
                Logger.d("onComplete")
            }
        )
    }

    fun onPause() {
        disposable?.dispose()
        disposable = null

        Logger.d("pause game engine")
    }

    fun updateTime(time: Long) {
        timeSubject.onNext(time)
        updateUnitAction(time)
    }

    private fun updateUnitAction(time: Long) {
        UnitActionManager.updateTime(time)
    }
}