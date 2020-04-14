package com.zs.mol.model.user

import com.zs.mol.model.unit.BattleUnit
import io.reactivex.subjects.PublishSubject

object UserManager {
    object ReservedUserId {
        const val GUEST = "guest"
        const val ENEMY = "enemy"
        const val NPC = "npc"
    }

    var user: User = User(ReservedUserId.GUEST)

    val updateSubject = PublishSubject.create<Boolean>()

    fun initUser(userId: String) {
        user = User(userId)
        gainGold(5000)
    }

    fun getUserId(): String {
        return user.id
    }

    fun getEnemyId(): String {
        return ReservedUserId.ENEMY
    }

    fun getUnits(): List<BattleUnit> {
        return user.units
    }

    fun isMyUnit(id: String): Boolean {
        return user.id === id
    }

    fun getGem(): Long {
        return user.userStatus.gem
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
