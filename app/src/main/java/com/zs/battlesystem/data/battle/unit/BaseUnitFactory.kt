package com.zs.battlesystem.data.battle.unit

import com.zs.battlesystem.data.battle.stat.BaseStat
import com.zs.battlesystem.data.battle.stat.SecondStat
import com.zs.battlesystem.data.const.ManNamePool
import com.zs.battlesystem.data.const.WomanNamePool
import kotlin.math.ceil

object BaseUnitFactory {
    private const val MAX_STAT = 18
    private const val MIN_STAT = 6

    fun create(): BaseUnit {
        val name = let {
            if (Math.random() < 0.5) {
                // man
                val index = (Math.random() * ManNamePool.NAME_LIST.size).toInt()
                ManNamePool.NAME_LIST[index]
            } else {
                // woman
                val index = (Math.random() * WomanNamePool.NAME_LIST.size).toInt()
                WomanNamePool.NAME_LIST[index]
            }
        }

        return BaseUnit().apply {
            this.name = name
            BaseStat.KEYS.forEach {
                this.originalStat.baseStat.values[it] = ceil(
                    (Math.random() * (MAX_STAT - MIN_STAT)) + MIN_STAT
                )
            }

            this.originalStat.secondStat = SecondStat.createFromBaseStat(originalStat.baseStat)
            this.calculateStat()
        }
    }
}