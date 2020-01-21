package com.zs.battlesystem.data.battle

import com.zs.battlesystem.data.battle.unit.BattleUnit

object BattleFunction {
    fun checkCritical(user: BattleUnit, target: BattleUnit): Boolean {
        return Math.random() * 100 < user.base.battleStat.critical
    }

    fun checkEvade(user: BattleUnit, target: BattleUnit): Boolean {
        return Math.random() * 100 < target.base.battleStat.evade
    }

    fun getDefaultAttackDamage(
        user: BattleUnit
    ): Int {
        val maxAttack = user.base.battleStat.maxAtk
        val minAttack = user.base.battleStat.minAtk

        return (Math.random() * (maxAttack - minAttack) + minAttack).toInt()
    }

    fun getDamageReductionRatio(damage: Int, defence: Int): Double {
        return if (damage > defence || defence == 0) {
            0.0
        } else {
            val surplusDefence = defence - damage
            surplusDefence / (damage + surplusDefence).toDouble()
        }
    }
}