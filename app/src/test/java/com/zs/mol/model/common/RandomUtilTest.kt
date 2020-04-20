package com.zs.mol.model.common

import org.junit.Test
import kotlin.random.Random

class RandomUtilTest {
    @Test
    fun testWeightRandom() {
        var weights = IntArray(100000).toTypedArray()
        weights = weights.map { Random.nextInt(100000) }.toTypedArray()
        //println(RandomUtil.weightedRandomIndex(weights))
        //println(RandomUtil.weightedIndex(weights))
        var current = System.currentTimeMillis()
        for (i in 0..10000) {
            RandomUtil.weightedIndex(weights)
        }
        println("time : " + (System.currentTimeMillis() - current))
        current = System.currentTimeMillis()
        for (i in 0..10000) {
            RandomUtil.weightedRandomIndex(weights)
        }
        println("time : " + (System.currentTimeMillis() - current))
        // 1 3 6 10 15 21
    }

    fun bb(weights: Array<Int>, randD: Int): Int {
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

    fun aa(weights: Array<Int>, randD: Int): Int {
        var sum = 0
        val weightedIndex = IntArray(weights.size)

        weights.forEachIndexed { index, i ->
            sum += i
            weightedIndex[index] = sum.toInt()
        }

        val randomValue = Random.nextInt(sum)
        val index = -1//weightedIndex.binarySearch(randomValue.toInt())
        if (index < 0) {
            return -index - 1
        }
        return index
    }
}