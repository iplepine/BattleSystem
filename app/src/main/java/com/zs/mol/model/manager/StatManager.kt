package com.zs.mol.model.manager

import com.zs.mol.model.stat.BaseStat
import com.zs.mol.model.stat.SecondStat

/**
 * 스탯 밸런스 관련 문서
 * https://docs.google.com/spreadsheets/d/1F_PV4X_6aQ_WyHP0N5boh4cwJhzMbEKSzsJ-C_UZJCI/edit#gid=733204273
 */

object StatManager {
    object Const {
        val BASE_STAT_MAX = 20
    }

    private val LEVEL_UP_DEFAULT_FACTORS = arrayOf(
        10, 5, 1, 2, 1, 0, 1, 0.05, 0.005, 0.05, 0.01
    )

    private val LEVEL_UP_FACTORS = arrayOf(
        arrayOf(3, 0, 0, 10, 0, 0, 0),
        arrayOf(0, 0, 3, 1, 5, 0, 0),
        arrayOf(0.1, 0, 0, 0.1, 0.3, 0.2, 0),
        arrayOf(1, 0.3, 0, 0, 0, 0, 0),
        arrayOf(0.1, 0.2, 0, 0.5, 0, 0, 0),
        arrayOf(0, 0, 1.5, 0, 0.5, 0, 0),
        arrayOf(0, 0, 0.2, 0.1, 0.4, 0.1, 0),
        arrayOf(0.005, 0.01, 0.002, 0, 0, 0.002, 0),
        arrayOf(0, 0.01, 0.005, 0, 0, 0, 0),
        arrayOf(0, 0.02, 0, 0.005, 0, 0, 0),
        arrayOf(0.002, 0.01, 0.005, 0, 0.002, 0.005, 0.001)
    )

    val defaultLevelUpFactor = HashMap<String, Double>().apply {
        SecondStat.KEYS.forEachIndexed { i, key ->
            put(key, (LEVEL_UP_DEFAULT_FACTORS[i] as Number).toDouble())
        }
    }

    val statFactors = HashMap<String, HashMap<String, Double>>().apply {
        for (i in BaseStat.KEYS.indices) {
            val statFactor = HashMap<String, Double>()
            val statKey = BaseStat.KEYS[i]

            for (j in LEVEL_UP_FACTORS.indices) {
                val secondStatKey = SecondStat.KEYS[j]
                statFactor[secondStatKey] = (LEVEL_UP_FACTORS[j][i] as Number).toDouble()
            }

            put(statKey, statFactor)
        }
    }
}