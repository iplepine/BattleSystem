package com.zs.mol.model.stat

import com.zs.mol.model.battle.BattleFunction

class SecondStat : HashMap<String, Double>() {

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
            KEYS.forEach { secondStatName ->
                BaseStat.KEYS.forEach { baseStatKey ->
                    val baseStat = baseStat[baseStatKey]
                    val factor = StatManager.statFactors[baseStatKey]?.get(secondStatName) ?: 0.0

                    val statBonus = BattleFunction.getStatBonus(baseStat)

                    var upStat = factor * baseStat + statBonus
                    if (useRandom) {
                        upStat *= Math.random()
                    }

                    secondStat[secondStatName] =
                        secondStat[secondStatName]?.plus(upStat)
                }
            }

            return secondStat
        }
    }

    override fun get(key: String): Double {
        return super.get(key) ?: 0.0
    }

    fun setInitialValue(value: Double) {
        KEYS.forEach {
            this[it] = value
        }
    }

    fun plus(stat: SecondStat) {
        KEYS.forEach {
            this[it] = get(it).plus(stat.get(it))
        }
    }

    fun plus(statValues: HashMap<String, Double>) {
        statValues.forEach {
            this[it.key] = get(it.key).plus(statValues[it.key]!!)
        }
    }

    fun times(stat: SecondStat) {
        KEYS.forEach {
            this[it] = get(it).times(stat.get(it))
        }
    }
}