package com.zs.mol.model.common

import kotlin.random.Random

object RandomUtil {
    fun rand(min: Double, max: Double): Double {
        return Math.random() * (max - min) + min
    }

    fun isOver(value: Double): Boolean {
        return Math.random() > value
    }

    fun isUnder(value: Double): Boolean {
        return Math.random() < value
    }

    fun weightedIndex(weights: Array<Int>): Int {
        var sum = 0
        weights.forEach { sum += it }
        var rand = Random.nextInt(sum)
        weights.forEachIndexed { index, i ->
            if (rand <= i) {
                return index
            }
            rand -= i
        }
        return 0
    }
}