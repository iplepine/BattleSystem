package com.zs.battlesystem.data.battle.skill

import com.zs.battlesystem.data.battle.unit.BattleUnit
import io.reactivex.subjects.PublishSubject

open abstract class Skill {
    var name: String = ""
    var description: String = ""

    var castingTime: Long = 0L
    var effectTime: Long = 0L
    var afterDelay: Long = 0L

    var coolTime = 0L
    var coolDown = 0L

    var targetType = TargetType.ENEMY
    var targetCount = 1

    var damageFactors: ArrayList<DamageFactor> = ArrayList()

    object TargetType {
        const val NON_TARGET = -1
        const val SELF = 0
        const val ENEMY = 1
        const val ALLIES = 2
        const val RANDOM = 3
    }

    fun startCoolDown() {
        coolDown = coolTime
    }

    open fun updateTime(time: Long) {
        coolDown -= time
        if (coolDown < 0) {
            coolDown = 0
        }
    }

    abstract fun onEffect(
        user: BattleUnit,
        target: BattleUnit,
        messageSubject: PublishSubject<String>?
    )

    fun onEffect(
        user: BattleUnit,
        targets: List<BattleUnit>,
        messageSubject: PublishSubject<String>?
    ) {
        targets.forEach { onEffect(user, it, messageSubject) }
    }

    open fun getExpectEffect(): Double {
        return 0.0
    }
}