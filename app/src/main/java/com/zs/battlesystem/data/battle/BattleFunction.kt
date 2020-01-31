package com.zs.battlesystem.data.battle

import com.zs.battlesystem.data.battle.stat.SecondStat.Companion.ATK
import com.zs.battlesystem.data.battle.stat.SecondStat.Companion.CRI
import com.zs.battlesystem.data.battle.stat.SecondStat.Companion.EVADE
import com.zs.battlesystem.data.battle.unit.BattleUnit

object BattleFunction {
    fun checkCritical(user: BattleUnit, target: BattleUnit): Boolean {
        return Math.random() * 100 < user.stat.secondStat.get(CRI)
    }

    fun checkEvade(user: BattleUnit, target: BattleUnit): Boolean {
        return Math.random() * 100 < target.stat.secondStat.get(EVADE)
    }

    fun getDefaultAttackDamage(
        user: BattleUnit
    ): Double {
        val attack = user.stat.secondStat.get(ATK)

        return (Math.random() * attack + attack / 5)
    }

    fun getDamageReductionRatio(damage: Double, defence: Double): Double {
        return if (damage > defence || defence == 0.0) {
            0.0
        } else {
            val surplusDefence = defence - damage
            surplusDefence / (damage + surplusDefence)
        }
    }
}