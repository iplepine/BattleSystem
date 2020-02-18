package com.zs.mol.model.battle.stat

data class Stat(
    var baseStat: BaseStat = BaseStat(HashMap()),
    var secondStat: SecondStat = SecondStat(HashMap())
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

    fun addValue(key: String, amount: Double) {
        if (BaseStat.isValidKey(key)) {
            baseStat.values[key] = (baseStat.values[key] ?: 0.0) + amount
        } else if (SecondStat.isValidKey(key)) {
            secondStat.values[key] = (secondStat.values[key] ?: 0.0) + amount
        }
    }
}