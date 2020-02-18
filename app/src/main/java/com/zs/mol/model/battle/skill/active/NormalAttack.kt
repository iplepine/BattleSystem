package com.zs.mol.model.battle.skill.active

import com.zs.mol.model.battle.BattleFunction
import com.zs.mol.model.battle.skill.Skill
import com.zs.mol.model.battle.stat.SecondStat.Companion.DEF
import com.zs.mol.model.battle.unit.BattleUnit
import com.zs.mol.model.common.Logger
import io.reactivex.subjects.PublishSubject

class NormalAttack : Skill() {
    init {
        name = "Normal Attack"
        castingTime = 0
        effectTime = 0
        afterDelay = 0

        coolDown = 0L

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

        Logger.d("attack : ${attack.toInt()}, defence : ${defence.toInt()}")

        var isCritical = BattleFunction.checkCritical(user, target)
        if (isCritical) {
            attack *= 2
            isCritical = true
        }

        var isBlocked = attack < defence
        val damage = if (isBlocked) {
            attack * (1 - BattleFunction.getDamageReductionRatio(attack, defence))
        } else {
            attack - defence
        }.toInt() * -1

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

        target.adjustHp(damage)
    }
}