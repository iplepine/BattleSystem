package com.zs.battlesystem.data.battle.skill.active

import com.zs.battlesystem.data.battle.BattleFunction
import com.zs.battlesystem.data.battle.skill.Skill
import com.zs.battlesystem.data.battle.unit.BattleUnit
import com.zs.battlesystem.data.common.Logger
import io.reactivex.subjects.PublishSubject

object NormalAttack : Skill() {
    init {
        name = "기본 공격"
        castingTime = 0
        effectTime = 0
        afterDelay = 0

        coolDown = 0L
    }

    override fun getExpectEffect(): Double {
        return 1.0
    }

    override fun onEffect(
        user: BattleUnit,
        targets: List<BattleUnit>,
        messageSubject: PublishSubject<String>?
    ) {
        require(targets.isNotEmpty())

        val target = targets[0]

        if (BattleFunction.checkEvade(user, target)) {
            val message = "공격을 회피하였습니다."
            messageSubject?.onNext(message)
            Logger.d(message)
            return
        }

        var attack = BattleFunction.getDefaultAttackDamage(user)
        val defence = target.base.battleStat.def

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