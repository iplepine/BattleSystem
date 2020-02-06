package com.zs.battlesystem.data.battle

import com.zs.battlesystem.data.battle.skill.Skill
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

    fun findTarget(
        user: BattleUnit,
        units: List<BattleUnit>,
        targetType: Int,
        targetCount: Int
    ): List<BattleUnit> {
        when (targetType) {
            Skill.TargetType.SELF ->
                return arrayListOf(user)

            Skill.TargetType.ENEMY ->
                return units.filter {
                    it.isEnemy(user.owner) && !it.isDie()
                }?.run { shuffled().subList(0, kotlin.math.min(size, targetCount)) }

            Skill.TargetType.ALLIES ->
                return units.filter { it.isMine(user.owner) }

            Skill.TargetType.RANDOM ->
                return units.shuffled().subList(0, targetCount)
        }

        return ArrayList()
    }
}