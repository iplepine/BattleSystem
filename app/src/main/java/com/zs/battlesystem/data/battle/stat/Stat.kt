package com.zs.battlesystem.data.battle.stat

data class Stat(
    val baseStat: BaseStat = BaseStat(HashMap()),
    val secondStat: SecondStat = SecondStat(HashMap())
) {
    companion object {
        fun createInitializedStat(baseInitialValue: Double, secondInitialValue: Double): Stat {
            return Stat(
                BaseStat(HashMap(), baseInitialValue),
                SecondStat(HashMap(), secondInitialValue)
            )
        }
    }

    fun deepCopy(): Stat {
        return let {
            Stat().also {
                it.baseStat.values.putAll(baseStat.values)
                it.secondStat.values.putAll(secondStat.values)
            }
        }
    }

    fun add(stat: Stat) {
        baseStat.plus(stat.baseStat)
        secondStat.plus(stat.secondStat)
    }

    fun multiple(stat: Stat) {
        baseStat.times(stat.baseStat)
        secondStat.times(stat.secondStat)
    }
}