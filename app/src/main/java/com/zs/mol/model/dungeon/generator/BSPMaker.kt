package com.zs.mol.model.dungeon.generator

import com.zs.mol.model.dungeon.DungeonPlace
import com.zs.mol.model.dungeon.generator.BSPMaker.BspNode.Companion.HORIZONTAL
import com.zs.mol.model.dungeon.generator.BSPMaker.BspNode.Companion.VERTICAL
import kotlin.math.max
import kotlin.random.Random

class BSPMaker(private var mapSize: Int, private var limitDepth: Int) : MapGenerator() {

    override fun createMap(): Array<Array<DungeonPlace>> {
        return Array(mapSize) { Array(mapSize) { DungeonPlace(DungeonPlace.PlaceType.WALL) } }
            .also { map ->
                val tree = createRandomBspTree()
                tree.root.markToMap(map)
            }
    }

    fun createRandomBspTree(): BspTree {
        val xRange = intArrayOf(0, mapSize - 1)
        val yRange = intArrayOf(0, mapSize - 1)
        val root = BspNode(null, xRange, yRange, 0)
        return BspTree(root).apply {
            root.divideRecursive(limitDepth)
        }
    }

    class BspTree(var root: BspNode) {

        fun findNode(node: BspNode?, x: Int, y: Int): BspNode? {
            if (node == null) return null
            if (node.hasPoint(x, y)) return node

            val divideValue = when (node.divideValue) {
                VERTICAL -> y
                HORIZONTAL -> x
                else -> return null
            }

            return if (divideValue <= node.divideValue) {
                findNode(node.left, x, y)
            } else {
                findNode(node.right, x, y)
            }
        }

        fun removeNode(node: BspNode) {
            if (node.parent?.left == node) {
                node.parent?.left = null
            }
            if (node.parent?.right == node) {
                node.parent?.right = null
            }

            node.parent = null
        }
    }

    class BspNode(
        var parent: BspNode? = null,
        val xRange: IntArray,
        val yRange: IntArray,
        val depth: Int
    ) {
        companion object {
            const val NO_DIVIDABLE = -1
            const val VERTICAL = 0
            const val HORIZONTAL = 1

            const val MIN_SIZE = 4
        }

        var left: BspNode? = null
        var right: BspNode? = null

        var divideValue = 0
        var divideType = NO_DIVIDABLE

        var isLeaf = false

        fun hasPoint(x: Int, y: Int): Boolean {
            return xRange[0] <= x && x <= xRange[1]
                    && yRange[0] <= y && y <= yRange[1]
        }

        fun divideRecursive(depthLimit: Int) {
            if (depthLimit <= depth) {
                setLeaf()
                return
            }

            divideType = calculateDivideType()
            when (divideType) {
                NO_DIVIDABLE -> {
                    setLeaf()
                    return
                }
                VERTICAL -> {
                    divideValue = calculateDivideValue(yRange[0], yRange[1])
                    left = BspNode(
                        this,
                        xRange.copyOf(),
                        intArrayOf(yRange[0], divideValue),
                        depth + 1
                    )
                    right = BspNode(
                        this,
                        xRange.copyOf(),
                        intArrayOf(divideValue + 1, yRange[1]),
                        depth + 1
                    )
                }
                else -> {
                    divideValue = calculateDivideValue(xRange[0], xRange[1])
                    left = BspNode(
                        this,
                        intArrayOf(xRange[0], divideValue),
                        yRange.copyOf(),
                        depth + 1
                    )
                    right = BspNode(
                        this,
                        intArrayOf(divideValue + 1, xRange[1]),
                        yRange.copyOf(),
                        depth + 1
                    )
                }
            }

            left?.divideRecursive(depthLimit)
            right?.divideRecursive(depthLimit)
        }

        private fun setLeaf() {
            isLeaf = true
        }

        private fun calculateDivideType(): Int {
            val dividableX = 2 * MIN_SIZE <= xRange[1] - xRange[0]
            val dividableY = 2 * MIN_SIZE <= yRange[1] - yRange[0]

            if (dividableX && dividableY) {
                return if (xRange[1] - xRange[0] < yRange[1] - yRange[0]) {
                    if (Random.nextFloat() < 0.7) {
                        VERTICAL
                    } else {
                        HORIZONTAL
                    }
                } else {
                    if (Random.nextFloat() < 0.7) {
                        HORIZONTAL
                    } else {
                        VERTICAL
                    }
                }
            } else if (dividableX) {
                return HORIZONTAL
            } else if (dividableY) {
                return VERTICAL
            }
            return NO_DIVIDABLE
        }

        private fun calculateDivideValue(start: Int, end: Int): Int {
            val mid = (end + start) / 2
            val fix = max(mid - start - MIN_SIZE, 0)

            return if (fix == 0) {
                mid
            } else {
                Random.nextInt(mid - fix, mid + fix)
            }
        }

        fun markToMap(map: Array<Array<DungeonPlace>>) {
            if (isLeaf) {
                for (i in xRange[0] + 1 until xRange[1]) {
                    for (j in yRange[0] + 1 until yRange[1]) {
                        map[i][j] = DungeonPlace(DungeonPlace.PlaceType.GROUND)
                    }
                }
            }

            left?.markToMap(map)
            right?.markToMap(map)
        }
    }
}
