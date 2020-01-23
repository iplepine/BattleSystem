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
        Logger.d("${user.base.name}이 ${target.base.name}에게 ${name}을 사용")

        if (BattleFunction.checkEvade(user, target)) {
            val message = "Miss!!"
            messageSubject?.onNext(message)
            Logger.d(message)
            return
        }

        var attack = BattleFunction.getDefaultAttackDamage(user)
        val defence = target.stat.def

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
            "BLOCK!! $damage 의 데미지를 입었다. "
        } else {
            if (isCritical) {
                "CRITICAL!! $damage 의 데미지를 입었다."
            } else {
                "$damage 의 데미지를 입었다."
            }
        }
        messageSubject?.onNext(message)
        Logger.d(message)

        Logger.d("${target.base.name} 체력 : ${target.stat.hp}/${target.base.stat.hp}")
    }
}