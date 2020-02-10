package com.zs.battlesystem.model.common

abstract class MonoBehaviour {
    val location = Location(0.0, 0.0)
    abstract fun updateTime(time: Long)

    class Location(val x: Double, val y: Double)
}