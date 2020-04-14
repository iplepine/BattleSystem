package com.zs.mol.model.dungeon

import com.zs.mol.model.battle.BattleFunction
import com.zs.mol.model.common.Logger
import com.zs.mol.model.unit.BattleUnit
import kotlin.random.Random

class CheckEvent(val bonusStat: String, val min: Int, val max: Int) : DungeonEvent() {

    fun check(unit: BattleUnit) {
        if (checkSuccess(unit)) {
            onSuccess()
            Logger.d("Success")
        } else {
            onFailed()
            Logger.d("Failed")
        }
    }

    private fun checkSuccess(unit: BattleUnit): Boolean {
        Logger.d("Check! (${max}중 $min 이상 성공, Bonus stat: $bonusStat)")
        val dice = Random.nextInt(max)
        val bonus = getBonus(unit)
        Logger.d("......$dice...(+$bonus)!!")
        return min <= dice + bonus
    }

    open fun getBonus(unit: BattleUnit): Int {
        val stat = unit.currentStat.baseStat[bonusStat]
        return BattleFunction.getStatBonus(stat)
    }
}