package com.zs.battlesystem.data.battle.skill.active

import com.zs.battlesystem.data.battle.BattleFunction
import com.zs.battlesystem.data.battle.skill.Skill
import com.zs.battlesystem.data.battle.unit.BattleUnit
import com.zs.battlesystem.data.common.Logger
import io.reactivex.subjects.PublishSubject

object Bash : Skill() {
    private const val damageRatio = 2.0

    init {
        name = "강타"
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
        if (BattleFunction.checkEvade(user, target)) {
            val message = "공격을 회피하였습니다."
            messageSubject?.onNext(message)
            Logger.d(message)
            return
        }

        var attack = (BattleFunction.getDefaultAttackDamage(user) * damageRatio).toInt()
        val defence = target.base.stat.def

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
            (attack * (1 - BattleFunction.getDamageReductionRatio(attack, defence))).toInt()
        }

        target.onDamage(damage)

        val message = if (isBlocked) {
            "BLOCK!! ${damage} 의 데미지를 입었다. "
        } else {
            if (isCritical) {
                "CRITICAL!! ${damage} 의 데미지를 입었다."
            } else {
                "${damage} 의 데미지를 입었다."
            }
        }
        messageSubject?.onNext(message)
        Logger.d(message)
    }
}