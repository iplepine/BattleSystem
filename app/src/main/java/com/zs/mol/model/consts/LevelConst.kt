package com.zs.mol.model.consts

import kotlin.math.max

object LevelConst {
    const val MAX_LEVEL = 20

    private val accumulateExps: LongArray by lazy {
        LongArray(MAX_LEVEL + 1).apply {
            this[0] = 0
            for (i in 1..MAX_LEVEL) {
                this[i] = this[i - 1] + getRequireExp(i)
            }
        }
    }

    fun getLevel(totalExp: Long): Int {
        accumulateExps.forEachIndexed { index, exp ->
            if (totalExp < exp) {
                return index
            }
        }

        return MAX_LEVEL
    }

    private fun getRequireExp(level: Int): Long {
        return level * (level + 2L)
    }

    fun getBalancedExp(exp: Long, expLevel: Int, takerLevel: Int): Long {
        val levelDiff = takerLevel - expLevel
        return max(1, (exp * (1 - levelDiff / 10.0)).toLong())
    }
}