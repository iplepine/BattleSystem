package com.zs.battlesystem.data.battle.skill

import com.zs.battlesystem.data.battle.unit.BattleUnit
import io.reactivex.subjects.PublishSubject
import kotlin.math.min

open abstract class Skill {
    var name: String = ""
    var description: String = ""

    var castingTime: Long = 0L
    var effectTime: Long = 0L
    var afterDelay: Long = 0L

    var coolTime = 0L   // 스킬의 쿨타임
    var coolDown = 0L   // 남은 쿨 타임

    var targetType = TargetType.ENEMY
    var targetCount = 1

    object TargetType {
        const val SELF = 0
        const val ENEMY = 1
        const val FRIEND = 2
        const val RANDOM = 3
    }

    fun reduceCooldown(time: Long) {
        coolDown -= time
        if (coolDown < 0) {
            coolDown = 0
        }
    }

    protected abstract fun onEffect(
        user: BattleUnit,
        target: BattleUnit,
        messageSubject: PublishSubject<String>?
    )

    fun onEffect(
        user: BattleUnit,
        targets: List<BattleUnit>,
        messageSubject: PublishSubject<String>?
    ) {
        val targets = findTarget(user, targets)
        targets.forEach { onEffect(user, it, messageSubject) }
        coolDown = coolTime
    }

    open fun getExpectEffect(): Double {
        return 0.0
    }

    open fun findTarget(user: BattleUnit, units: List<BattleUnit>): List<BattleUnit> {
        when (targetType) {
            TargetType.SELF ->
                return arrayListOf(user)

            TargetType.ENEMY ->
                return units.filter {
                    it.isEnemy(user.owner) && !it.isDie()
                }?.run { shuffled().subList(0, min(size, targetCount)) }

            TargetType.FRIEND ->
                return units.filter { it.isMine(user.owner) && it != user }

            TargetType.RANDOM ->
                return units.shuffled().subList(0, targetCount)
        }

        return ArrayList()
    }
}