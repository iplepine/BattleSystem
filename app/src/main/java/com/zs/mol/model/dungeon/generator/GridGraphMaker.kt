package com.zs.mol.model.dungeon.generator

import com.zs.mol.model.common.Position
import com.zs.mol.model.dungeon.DungeonPlace
import com.zs.mol.model.dungeon.generator.BSPMaker.BspNode.Companion.HORIZONTAL
import com.zs.mol.model.dungeon.generator.BSPMaker.BspNode.Companion.VERTICAL
import kotlin.math.min
import kotlin.random.Random

class GridGraphMaker(val width: Int, val height: Int, val difficulty: Int) : MapGenerator() {

    var entrance: Position = Position(Random.nextInt(width), Random.nextInt(height))

    val edgeList = ArrayList<Edge>()
    var map = Array(width) { IntArray(height) { FieldType.WALL } }
    var visitTable = Array(width) { BooleanArray(height) { false } }
    var x = entrance.x
    var y = entrance.y

    val directions = ArrayList<DungeonPlace.Direction>()
    lateinit var direction: DungeonPlace.Direction

    init {
        visitTable[x][y] = true
        map[x][y] = FieldType.ENTRANCE
    }

    override fun createMap(): Array<IntArray> {
        return Array(width * 2) { IntArray(height * 2) { FieldType.WALL } }.apply {
            makeRooms(difficulty)
            for (i in map.indices) {
                for (j in map[i].indices) {
                    this[i * 2][j * 2] = map[i][j]
                }
            }

            edgeList.forEach {
                if (it.type == VERTICAL) {
                    this[it.end.x * 2][min(it.start.y, it.end.y) * 2 + 1] = FieldType.VERTICAL_WAY
                } else if (it.type == HORIZONTAL) {
                    this[min(it.start.x, it.end.x) * 2 + 1][it.end.y * 2] = FieldType.HORIZONTAL_WAY
                }
            }
        }
    }

    private fun makeRooms(roomCount: Int) {
        var madeCount = 0
        var tryCount = 0
        val TRY_LIMIT = 1000

        while (madeCount < roomCount && tryCount++ < TRY_LIMIT) {
            val directionList = DungeonPlace.Direction.values()
                .filterNot { it == DungeonPlace.Direction.NONE }
                .toList()
                .shuffled()

            for (i in directionList.indices) {
                val d = directionList[i]
                if (makeRoom(d)) {
                    directions.add(d)
                    madeCount++
                    break
                }
            }
        }
    }

    private fun makeRoom(direction: DungeonPlace.Direction): Boolean {
        this.direction = direction
        when (direction) {
            DungeonPlace.Direction.NORTH -> y -= 1
            DungeonPlace.Direction.EAST -> x += 1
            DungeonPlace.Direction.SOUTH -> y += 1
            DungeonPlace.Direction.WEST -> x -= 1
        }

        // 맵 범위 벗어난 경우
        if (x !in 0 until width || y !in 0 until height) {
            moveBack()
            return false
        }

        // 이미 방문하거나 맵이 그려져 있을 경우
        if (visitTable[x][y] || map[x][y] != FieldType.WALL) {
            moveBack()
            return false
        }

        // 더이상 확장 할 수 없는 경우
        if (!canSpread(map, x, y)) {
            moveBack()
            return false
        }

        visitTable[x][y] = true
        map[x][y] = FieldType.GROUND
        addEdge(x, y, direction)

        println("create map : $x, $y")
        return true
    }

    private fun canSpread(map: Array<IntArray>, x: Int, y: Int): Boolean {
        return (x + 1 < width && visitTable[x + 1][y] && map[x + 1][y] != FieldType.WALL)
                || (0 <= x - 1 && visitTable[x - 1][y] && map[x - 1][y] != FieldType.WALL)
                || (y + 1 < height && visitTable[x][y + 1] && map[x][y + 1] != FieldType.WALL)
                || (0 <= y - 1 && visitTable[x][y - 1] && map[x][y - 1] != FieldType.WALL)
    }

    private fun moveBack() {
        when (direction) {
            DungeonPlace.Direction.NORTH -> y += 1
            DungeonPlace.Direction.EAST -> x -= 1
            DungeonPlace.Direction.SOUTH -> y -= 1
            DungeonPlace.Direction.WEST -> x += 1
        }
    }

    private fun addEdge(x: Int, y: Int, direction: DungeonPlace.Direction) {
        var type = VERTICAL
        var start = Position(x, y)
        var end = Position(x, y)
        when (direction) {
            DungeonPlace.Direction.NORTH -> {
                start.y = y + 1
            }
            DungeonPlace.Direction.EAST -> {
                start.x = x - 1
                type = HORIZONTAL
            }
            DungeonPlace.Direction.SOUTH -> {
                start.y = y - 1
            }
            DungeonPlace.Direction.WEST -> {
                start.x = x + 1
                type = HORIZONTAL
            }
        }

        val edge = Edge(type, start, end)
        edgeList.add(edge)
    }

    class Edge(val type: Int, val start: Position, val end: Position)
}