package com.zs.mol.model.stat

import java.util.*
import kotlin.math.min
import kotlin.random.Random

object StatFactory {
    const val MAX_STAT = 18
    const val MIN_STAT = 6

    private const val BASE_STAT_COUNT = 7
    private const val MAX_STAT_SUM = MAX_STAT * BASE_STAT_COUNT
    private const val MIN_STAT_SUM = MIN_STAT * BASE_STAT_COUNT
    private const val SPECIAL_BONUS = 2
    private const val SPECIAL_BONUS_RATIO = 0.001

    private fun randomBaseStat(): BaseStat {
        val baseStat = BaseStat()

        var remainingStat = Random.nextInt(MAX_STAT_SUM - MIN_STAT_SUM)
        var statKeyList = BaseStat.KEYS.toList()
        Collections.shuffle(statKeyList)

        statKeyList.forEach {
            val randomStat = Random.nextInt(MAX_STAT - MIN_STAT)
            val stat = min(randomStat, remainingStat)
            baseStat[it] = (MIN_STAT + stat).toDouble()
            remainingStat -= stat
        }

        if (Random.nextDouble() < SPECIAL_BONUS_RATIO) {
            for (i in 0 until SPECIAL_BONUS) {
                Collections.shuffle(statKeyList)
                val key = statKeyList[i]
                baseStat[key] = baseStat[key] + 1
            }
        }

        return baseStat
    }

    fun randomStat(): Stat {
        return Stat().apply {
            baseStat = randomBaseStat()
            secondStat = SecondStat.createFromBaseStat(baseStat)
        }
    }
}