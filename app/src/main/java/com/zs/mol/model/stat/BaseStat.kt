package com.zs.mol.model.stat

class BaseStat : HashMap<String, Double>() {

    companion object {
        const val STR = "STR"
        const val DEX = "DEX"
        const val INT = "INT"
        const val CON = "CON"
        const val WIS = "WIS"
        const val CHA = "CHA"
        const val LUCK = "LUCK"

        val KEYS = arrayOf(STR, DEX, INT, CON, WIS, CHA, LUCK)

        fun isValidKey(key: String): Boolean {
            return KEYS.contains(key)
        }
    }

    override fun get(key: String): Double {
        return super.get(key) ?: 0.0
    }

    fun get(key: String, default: Double = 0.0): Double {
        return super.get(key) ?: default
    }

    fun setInitialValue(value: Double) {
        KEYS.forEach {
            this[it] = value
        }
    }

    fun plus(stat: BaseStat) {
        KEYS.forEach {
            this[it] = get(it).plus(stat.get(it))
        }
    }

    fun plus(statValues: HashMap<String, Double>) {
        statValues.forEach {
            this[it.key] = get(it.key).plus(statValues[it.key]!!)
        }
    }

    fun times(stat: BaseStat) {
        KEYS.forEach {
            this[it] = get(it).times(stat.get(it))
        }
    }
}