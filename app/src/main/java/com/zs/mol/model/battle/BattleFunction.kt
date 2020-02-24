package com.zs.mol.model.battle

import com.zs.mol.model.skill.Skill
import com.zs.mol.model.stat.SecondStat
import com.zs.mol.model.stat.SecondStat.Companion.ATK
import com.zs.mol.model.stat.SecondStat.Companion.CRI
import com.zs.mol.model.stat.SecondStat.Companion.EVADE
import com.zs.mol.model.stat.SecondStat.Companion.HIT
import com.zs.mol.model.unit.BattleUnit
import kotlin.math.min

object BattleFunction {
    fun checkCritical(user: BattleUnit, target: BattleUnit): Boolean {
        return Math.random() * 100 < user.currentStat.secondStat.get(CRI)
    }

    fun checkEvade(user: BattleUnit, target: BattleUnit): Boolean {
        val hit = user.currentStat.secondStat.get(HIT)
        val evade = target.currentStat.secondStat.get(EVADE)
        return hit + Math.random() * 100 < evade
    }

    fun getDefaultAttackDamage(
        user: BattleUnit
    ): Double {
        val attack = user.currentStat.secondStat.get(ATK)

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
        targetType: Skill.TargetType,
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

    fun calculateUnitTurnDelay(unit: BattleUnit): Long {
        return min(
            BattleUnit.MINIMUM_DELAY, (BattleUnit.DEFAULT_DELAY - unit.currentStat.secondStat.get(
                SecondStat.SPEED
            )).toLong())
    }
}