package com.zs.mol.model.battle.skill.active

import com.zs.mol.model.battle.BattleFunction
import com.zs.mol.model.battle.skill.Skill
import com.zs.mol.model.battle.stat.SecondStat.Companion.DEF
import com.zs.mol.model.battle.unit.BattleUnit
import com.zs.mol.model.common.Logger
import io.reactivex.subjects.PublishSubject

class Bash : Skill() {
    companion object {
        private const val damageRatio = 2.0
    }

    init {
        name = "Bash"
        description = "힘을 모아 강하게 내려 친다"
        castingTime = 0
        effectTime = 0
        afterDelay = 0

        coolTime = 1000 * 10L   // 쿨타임 10초
    }

    override fun getExpectEffect(): Double {
        return damageRatio
    }

    override fun onEffect(
        user: BattleUnit,
        target: BattleUnit,
        messageSubject: PublishSubject<String>?
    ) {
        Logger.d("${user.base.name} use $name to ${target.base.name}")

        if (BattleFunction.checkEvade(user, target)) {
            val message = "Miss!!"
            messageSubject?.onNext(message)
            Logger.d(message)
            return
        }

        var attack = (BattleFunction.getDefaultAttackDamage(user) * damageRatio)
        val defence = target.stat.secondStat.get(DEF)

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

        target.adjustHp(damage)

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
    }
}