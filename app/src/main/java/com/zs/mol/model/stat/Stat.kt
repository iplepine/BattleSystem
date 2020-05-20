package com.zs.mol.model.stat

data class Stat(
    var baseStat: BaseStat = BaseStat(),
    var secondStat: SecondStat = SecondStat()
) {
    companion object {
        fun createInitializedStat(baseInitialValue: Double, secondInitialValue: Double): Stat {
            return Stat(
                BaseStat().apply { setInitialValue(baseInitialValue) },
                SecondStat().apply { setInitialValue(secondInitialValue) }
            )
        }
    }

    fun deepCopy(): Stat {
        return let {
            Stat().also {
                it.baseStat.putAll(baseStat)
                it.secondStat.putAll(secondStat)
            }
        }
    }

    fun getStat(key: String): Double {
        return when {
            baseStat.containsKey(key) -> baseStat[key]
            secondStat.containsKey(key) -> secondStat[key]
            else -> 0.0
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

    fun addValue(key: String, amount: Double) {
        if (BaseStat.isValidKey(key)) {
            baseStat[key] = (baseStat[key]) + amount
        } else if (SecondStat.isValidKey(key)) {
            secondStat[key] = (secondStat[key]) + amount
        }
    }
}