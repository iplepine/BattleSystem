package com.zs.mol.model.stat

import com.zs.mol.model.common.RandomUtil
import com.zs.mol.model.const.ManNamePool
import com.zs.mol.model.const.WomanNamePool
import com.zs.mol.model.unit.BaseUnit
import kotlin.math.ceil

object StatFactory {
    private const val MAX_STAT = 18.0
    private const val MIN_STAT = 6.0

    fun randomName(): String {
        return if (Math.random() < 0.5) {
            // man
            val index = (Math.random() * ManNamePool.NAME_LIST.size).toInt()
            ManNamePool.NAME_LIST[index]
        } else {
            // woman
            val index = (Math.random() * WomanNamePool.NAME_LIST.size).toInt()
            WomanNamePool.NAME_LIST[index]
        }
    }

    fun randomStat(): Stat {
        return Stat().apply {
            BaseStat.KEYS.forEach {
                baseStat.values[it] = ceil(
                    RandomUtil.rand(
                        MIN_STAT,
                        MAX_STAT
                    )
                )

                secondStat = SecondStat.createFromBaseStat(baseStat)
            }
        }
    }
}