package com.zs.mol.model.stat

import com.zs.mol.model.common.RandomUtil
import com.zs.mol.model.const.ManNamePool
import com.zs.mol.model.const.WomanNamePool
import com.zs.mol.model.stat.StatManager.Const.MAX_STAT
import com.zs.mol.model.stat.StatManager.Const.MIN_STAT
import com.zs.mol.model.unit.BaseUnit
import kotlin.math.ceil

object StatFactory {

    fun randomStat(): Stat {
        return Stat().apply {
            BaseStat.KEYS.forEach {
                baseStat[it] = ceil(
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