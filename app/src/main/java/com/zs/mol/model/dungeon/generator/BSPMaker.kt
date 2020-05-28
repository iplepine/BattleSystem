package com.zs.mol.model.dungeon.generator

import kotlin.random.Random

class BSPMaker(private var mapSize: Int) : MapGenerator() {

    override fun createMap(): Array<IntArray> {
        return Array(mapSize) { IntArray(mapSize) }.also { map ->

        }
    }

    fun createRandomBspTree(): BspTree {
        val range = intArrayOf(0, mapSize)
        val root = BspNode(null, range, range, 0)
        return BspTree(root).apply {
        }
    }


    class BspTree(var root: BspNode) {
    }

    class BspNode(
        val parent: BspNode? = null,
        val xRange: IntArray,
        val yRange: IntArray,
        val depth: Int
    ) {
        companion object {
            const val VERTICAL = 0
            const val HORIZONTAL = 1

            const val MIN_SIZE = 3
            const val DIVIDE_RATIO = 0.2
        }

        var left: BspNode? = null
        var right: BspNode? = null

        var divideValue = 0
        var divideType = VERTICAL

        var isLeaf = false

        fun checkDividable(): Boolean {
            if (xRange[1] - xRange[0] < MIN_SIZE && yRange[1] - yRange[0] < MIN_SIZE) {
                isLeaf = true
                return false
            }

            return true
        }

        fun divide() {
            if (checkDividable()) {
                isLeaf = true
                return
            }

            val type = if (xRange[1] - xRange[0] < yRange[1] - yRange[0]) {
                VERTICAL
            } else {
                HORIZONTAL
            }


            if (type == VERTICAL) {
                divideValue = calculateDivideValue(yRange[0], yRange[1])
                left = BspNode(this, xRange, intArrayOf(yRange[0], divideValue - 1), depth + 1)
                right = BspNode(this, xRange, intArrayOf(divideValue + 1, yRange[1]), depth + 1)
            } else {
                divideValue = calculateDivideValue(xRange[0], xRange[1])
                left = BspNode(this, intArrayOf(xRange[0], divideValue - 1), yRange, depth + 1)
                right = BspNode(this, intArrayOf(divideValue, xRange[1]), yRange, depth + 1)
            }
        }

        fun calculateDivideValue(start: Int, end: Int): Int {
            val diff = end - start
            var fix = (diff * DIVIDE_RATIO).toInt()
            if (diff < fix * 2) {
                fix = 0
            }

            return Random.nextInt(start + fix, end - fix)
        }
    }
}
