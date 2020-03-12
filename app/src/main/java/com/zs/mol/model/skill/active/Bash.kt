package com.zs.mol.model.skill.active

import com.zs.mol.model.battle.BattleFunction
import com.zs.mol.model.common.Logger
import com.zs.mol.model.db.skill.SkillDB
import com.zs.mol.model.skill.Skill
import com.zs.mol.model.stat.SecondStat.Companion.DEF
import com.zs.mol.model.unit.BattleUnit
import io.reactivex.subjects.PublishSubject

object Bash : Skill(SkillDB.Key.Bash) {
    override fun getExpectEffect(): Int {
        return 2
    }

    override fun calculateDamage(unit: BattleUnit): Double {
        return super.calculateDamage(unit) * 2
    }

    override fun onEffect(
        user: BattleUnit,
        target: BattleUnit,
        messageSubject: PublishSubject<String>?
    ) {
        Logger.d("${user.getName()} use ${skillData.name} to ${target.getName()}")

        if (BattleFunction.checkEvade(user, target)) {
            val message = "Miss!!"
            messageSubject?.onNext(message)
            Logger.d(message)
            return
        }

        var attack = calculateDamage(user)
        val defence = target.totalStat.secondStat.get(DEF)

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