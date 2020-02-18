package com.zs.mol.model.battle.stat

import com.zs.mol.model.manager.StatManager

data class SecondStat(
    val values: HashMap<String, Double> = HashMap(),
    private val initialValue: Double = 0.0
) {
    init {
        if (values.isEmpty()) {
            KEYS.forEach {
                values[it] = initialValue
            }
        }
    }

    companion object {
        const val HP = "HP"
        const val MP = "MP"
        const val WILL = "WILL"
        const val ATK = "ATK"
        const val MATK = "MATK"
        const val DEF = "DEF"
        const val MDEF = "MDEF"
        const val HIT = "HIT"
        const val EVADE = "EVADE"
        const val SPEED = "SPEED"
        const val CRI = "CRI"

        val KEYS = arrayOf(
            HP, MP, WILL, ATK, MATK, DEF, MDEF, HIT, EVADE, SPEED, CRI
        )

        fun isValidKey(key: String): Boolean {
            return KEYS.contains(key)
        }

        fun createFromBaseStat(baseStat: BaseStat, useRandom: Boolean = true): SecondStat {
            val secondStat = SecondStat()
            secondStat.values.forEach { second ->
                val secondStatName = second.key

                baseStat.values.forEach { base ->
                    val baseStatKey = base.key
                    val baseStat = baseStat.values[baseStatKey] ?: 0.0
                    val factor = StatManager.statFactors[baseStatKey]?.get(secondStatName) ?: 0.0

                    val statBonus = 1 + (baseStat - (StatManager.Const.BASE_STAT_MAX / 2)) / 10.0

                    var upStat = factor * baseStat// * statBonus
                    if (useRandom) {
                        upStat *= Math.random()
                    }

                    secondStat.values[secondStatName] =
                        secondStat.values[secondStatName]?.plus(upStat) ?: 0.0
                }
            }

            return secondStat
        }
    }

    fun get(key: String): Double {
        return values[key] ?: 0.0
    }

    fun plus(stat: SecondStat) {
        KEYS.forEach {
            values[it] = get(it).plus(stat.get(it))
        }
    }

    fun plus(statValues: HashMap<String, Double>) {
        statValues.forEach {
            values[it.key] = get(it.key).plus(statValues[it.key]!!)
        }
    }

    fun times(stat: SecondStat) {
        KEYS.forEach {
            values[it] = get(it).times(stat.get(it))
        }
    }
}