package com.zs.battlesystem.data.battle.skill.active

import com.zs.battlesystem.data.battle.BattleFunction
import com.zs.battlesystem.data.battle.skill.Skill
import com.zs.battlesystem.data.battle.unit.BattleUnit
import com.zs.battlesystem.data.battle.stat.SecondStat.Companion.DEF
import com.zs.battlesystem.data.battle.stat.SecondStat.Companion.HP
import com.zs.battlesystem.data.common.Logger
import io.reactivex.subjects.PublishSubject

class NormalAttack : Skill() {
    init {
        name = "Normal Attack"
        castingTime = 0
        effectTime = 0
        afterDelay = 0

        coolDown = 0L

        targetType = TargetType.ENEMY
        targetCount = 1
    }

    override fun getExpectEffect(): Double {
        return 1.0
    }

    override fun onEffect(
        user: BattleUnit,
        target: BattleUnit,
        messageSubject: PublishSubject<String>?
    ) {
        Logger.d("${user.base.name} use [$name] to ${target.base.name}")

        if (BattleFunction.checkEvade(user, target)) {
            val message = "Miss!!"
            messageSubject?.onNext(message)
            Logger.d(message)
            return
        }

        var attack = BattleFunction.getDefaultAttackDamage(user)
        val defence = target.stat.secondStat.get(DEF)

        var isCritical = BattleFunction.checkCritical(user, target)
        if (isCritical) {
            attack *= 2
            isCritical = true
        }

        var isBlocked = defence < attack
        val damage = if (defence < attack) {
            attack - defence
        } else {
            isBlocked = true
            attack * (1 - BattleFunction.getDamageReductionRatio(attack, defence))
        }.toInt()

        target.onDamage(damage)

        val message = if (isBlocked) {
            "BLOCK!! $damage"
        } else {
            if (isCritical) {
                "CRITICAL!! $damage"
            } else {
                "$damage"
            }
        }
        messageSubject?.onNext(message)
        Logger.d(message)

        Logger.d(
            "${target.base.name} " +
                    "HP : ${target.stat.secondStat.get(HP)}/${target.base.totalStat.secondStat.get(
                        HP
                    )}\n"
        )
    }
}