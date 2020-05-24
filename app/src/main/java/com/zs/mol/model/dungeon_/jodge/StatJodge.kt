package com.zs.mol.model.dungeon_.jodge

import com.zs.mol.model.unit.BattleUnit
import kotlin.random.Random

abstract class StatJodge(var statName: String, var passStat: Double) : Jodge<BattleUnit>() {
    override fun check(unit: BattleUnit): Boolean {
        val result = dice(unit.currentStat.getStat(statName))
        return result > passStat
    }

    private fun dice(value: Double): Double {
        return Random.nextDouble(value)
    }
}