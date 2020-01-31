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

    fun add(stat: Stat) {
        baseStat.add(stat.baseStat)
        secondStat.add(stat.secondStat)
    }

    fun multiple(stat: Stat) {
        baseStat.multiple(stat.baseStat)
        secondStat.multiple(stat.secondStat)
    }
}