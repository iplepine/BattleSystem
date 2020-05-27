package com.zs.mol.model.dungeon.dice

import com.zs.mol.model.battle.BattleFunction
import com.zs.mol.model.common.Logger
import com.zs.mol.model.unit.BattleUnit
import kotlin.random.Random

abstract class StatDiceCheck(
    var statName: String, private val successLimit: Int, private val diceMax: Int
) : DiceCheck<BattleUnit>() {

    override fun check(unit: BattleUnit): Boolean {
        return checkSuccess(unit)
    }

    private fun checkSuccess(unit: BattleUnit): Boolean {
        Logger.d("Check! (${diceMax}중 $successLimit 이상 성공, Bonus stat: $statName)")
        val dice = Random.nextInt(diceMax)
        val bonus = getBonus(unit)
        Logger.d("......$dice...(+$bonus)!!")
        return successLimit <= dice + bonus
    }

    private fun getBonus(unit: BattleUnit): Int {
        val stat = unit.currentStat.baseStat[statName]
        return BattleFunction.getStatBonus(stat)
    }
}