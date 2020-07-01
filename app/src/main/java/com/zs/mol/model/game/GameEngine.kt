package com.zs.mol.model.game

import android.content.Context
import com.zs.mol.di.scope.GameScope
import com.zs.mol.model.common.Logger
import com.zs.mol.model.db.user.UserRepository
import com.zs.mol.model.unit.action.UnitActionManager
import com.zs.mol.model.user.User
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@GameScope
class GameEngine @Inject constructor(
    private val userRepository: UserRepository,
    private val user: User,
    private val unitActionManager: UnitActionManager
) {
    companion object {
        private const val TIME_PERIOD = 100L
    }

    val timeSubject: PublishSubject<Long> = PublishSubject.create()
    private var disposable: Disposable? = null

    fun saveGame() {
        Logger.d("save the game")
        userRepository.saveUser(user)
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
        unitActionManager.updateTime(time)
    }
}