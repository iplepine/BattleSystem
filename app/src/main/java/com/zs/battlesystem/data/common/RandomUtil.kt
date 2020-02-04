package com.zs.battlesystem.data.common

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
}