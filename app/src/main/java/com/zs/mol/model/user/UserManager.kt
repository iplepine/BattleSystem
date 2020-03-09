package com.zs.mol.model.user

import com.zs.mol.model.unit.BattleUnit
import io.reactivex.subjects.PublishSubject

object UserManager {
    var user: User = User("init")

    val updateSubject = PublishSubject.create<Boolean>()

    fun initUser(userId: String) {
        user = User(userId)
    }

    fun getUserId(): String {
        return user.id
    }

    fun getEnemyId(): String {
        return "enemy"
    }

    fun getUnits(): List<BattleUnit> {
        return user.units
    }

    fun isMyUnit(id: String): Boolean {
        return user.id === id
    }

    fun getGold(): Long {
        return user.userStatus.gold
    }

    fun gainGold(amount: Long) {
        user.userStatus.gainGold(amount)
        updateSubject.onNext(true)
    }

    fun useGold(amount: Long) {
        user.userStatus.useGold(amount)
        updateSubject.onNext(true)
    }
}
