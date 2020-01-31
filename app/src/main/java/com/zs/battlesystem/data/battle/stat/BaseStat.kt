package com.zs.battlesystem.data.battle.stat

data class BaseStat(
    val values: HashMap<String, Double> = HashMap(),
    var initialValue: Double = 0.0
) {
    init {
        if (values.isEmpty()) {
            KEYS.forEach {
                values[it] = initialValue
            }
        }
    }

    companion object {
        const val STR = "STR"
        const val DEX = "DEX"
        const val INT = "INT"
        const val CON = "CON"
        const val WIS = "WIS"
        const val CHA = "CHA"
        const val LUCK = "LUCK"

        val KEYS = arrayOf(STR, DEX, INT, CON, WIS, CHA, LUCK)
    }

    fun get(key: String, default: Double = 0.0): Double {
        return values[key] ?: default
    }

    fun add(stat: BaseStat) {
        KEYS.forEach {
            values[it] = get(it).plus(stat.get(it))
        }
    }

    fun add(statValues: HashMap<String, Double>) {
        statValues.forEach {
            values[it.key] = get(it.key).plus(statValues[it.key]!!)
        }
    }

    fun multiple(stat: BaseStat) {
        KEYS.forEach {
            values[it] = get(it).times(stat.get(it))
        }
    }
}