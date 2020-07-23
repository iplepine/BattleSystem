package com.zs.mol.model.dungeon.generator

import com.zs.mol.model.common.Position
import com.zs.mol.model.dungeon.Dungeon
import com.zs.mol.model.dungeon.DungeonPlace
import kotlin.random.Random

class TileAndGraphBasedMaker(private val width: Int, private val height: Int) :
    MapGenerator() {

    override fun createMap(): Array<Array<DungeonPlace>> {
        return Builder(width, height).initRandomEntrance()
            .makeRooms(10)
            .build()
    }

    class TiledPlace(x: Int, y: Int, type: PlaceType = PlaceType.GROUND) :
        DungeonPlace(type) {

        companion object {
            fun createHashKey(x: Int, y: Int): String {
                return "$x,$y"
            }
        }

        val position = Position(x, y)

        override fun getId(): String {
            return createHashKey(position.x, position.y)
        }
    }

    class TiledDungeon(start: TiledPlace, map: DungeonMap<TiledPlace>) :
        Dungeon<TiledPlace>(map, start) {
        fun getPlace(x: Int, y: Int): TiledPlace {
            return getPlace(TiledPlace.createHashKey(x, y)) ?: TiledPlace(x, y)
        }
    }

    class VisitMap(val width: Int, val height: Int) {
        private val map = Array(height) { BooleanArray(width) { false } }

        fun canVisit(x: Int, y: Int): Boolean {
            return x in 0 until height &&
                    y in 0 until width &&
                    !isVisit(x, y)
        }

        fun isVisit(x: Int, y: Int): Boolean {
            return map[x][y]
        }

        fun visit(x: Int, y: Int) {
            map[x][y] = true
        }

        fun visitBack(x: Int, y: Int) {
            map[x][y] = false
        }
    }

    class Builder(val width: Int, val height: Int) {
        var entrance: TiledPlace? = null
        var currentPosition = Position(0, 0)
        lateinit var direction: DungeonPlace.Direction

        val map = Dungeon.DungeonMap<TiledPlace>(width, height)
        var visitMap = VisitMap(width, height)
        lateinit var dungeon: TiledDungeon

        fun build(): Array<Array<DungeonPlace>> {
            return Array(2 * width - 1) { Array(2 * height - 1) { DungeonPlace(DungeonPlace.PlaceType.WALL) } }.also { ret ->
                for (i in 0 until map.width) {
                    for (j in 0 until map.height) {
                        ret[i * 2][j * 2] =
                            map.get(Position(i, j)) ?: DungeonPlace(DungeonPlace.PlaceType.WALL)
                    }
                }

                dungeon.places.apply {
                    keys.forEach { key ->
                        get(key)?.apply {
                            if (connects.containsKey(DungeonPlace.Direction.EAST)) {
                                ret[position.x * 2 + 1][position.y * 2] =
                                    DungeonPlace(DungeonPlace.PlaceType.HORIZONTAL_WAY)
                            }

                            if (connects.containsKey(DungeonPlace.Direction.SOUTH)) {
                                ret[position.x * 2][position.y * 2 + 1] =
                                    DungeonPlace(DungeonPlace.PlaceType.VERTICAL_WAY)
                            }
                        }
                    }
                }
            }
        }

        fun initRandomEntrance(): Builder {
            var x = 0
            var y = 0
            when (Random.nextInt(4)) {
                0 -> {
                    x = Random.nextInt(height)
                }
                1 -> {
                    x = height - 1
                    y = Random.nextInt(width)
                }
                2 -> {
                    x = Random.nextInt(height)
                    y = width - 1
                }
                3 -> {
                    y = Random.nextInt(width)
                }
            }
            return setEntrance(x, y)
        }

        fun setEntrance(x: Int, y: Int): Builder {
            if (entrance != null) {
                throw IllegalStateException("Entrance is already initialized.")
            }
            entrance = TiledPlace(x, y, DungeonPlace.PlaceType.ENTRANCE)?.also {
                currentPosition = Position(x, y)
                visitMap.visit(it.position.x, it.position.y)
                map.set(it.position, it)
                dungeon = TiledDungeon(it, map)
                dungeon.addPlace(it)
            }
            return this
        }

        fun makeRooms(roomCount: Int, repeatCount: Int = 1): Builder {
            val start = Position(currentPosition.x, currentPosition.y)
            if (makeRooms(roomCount, start, visitMap, repeatCount)) {
                return this
            } else {
                throw Throwable("can not make rooms")
            }
        }

        private fun printWays(way: ArrayList<DungeonPlace.Direction>) {
            way.forEach {
                val value = when (it) {
                    DungeonPlace.Direction.NORTH -> "Up"
                    DungeonPlace.Direction.EAST -> "Right"
                    DungeonPlace.Direction.SOUTH -> "Down"
                    DungeonPlace.Direction.WEST -> "Left"
                    DungeonPlace.Direction.NONE -> "N"
                }

                print("$value ")
            }
            println()
        }

        private fun connectRooms(current: Position, ways: ArrayList<DungeonPlace.Direction>) {
            ways.forEach {
                addRoom(current, it)
            }
        }

        fun addRoom(current: Position, direction: DungeonPlace.Direction): Builder {
            val currentRoom = dungeon.getPlace(current.x, current.y)
            current.moveDirection(direction)
            val newPlace = dungeon.getPlace(current.x, current.y)
            map.set(current, newPlace)
            dungeon.connectPlace(currentRoom, direction, newPlace)

            return this
        }

        private fun makeRooms(
            roomCount: Int,
            start: Position,
            visitMap: VisitMap,
            repeatCount: Int
        ): Boolean {
            visitMap.visit(start.x, start.y)
            val current = Position(start.x, start.y)

            val tryLimit = 100000
            var tryCount = 0
            val way = ArrayList<DungeonPlace.Direction>()

            while (tryCount++ < tryLimit && !isComplete(way, roomCount)) {
                val direction = availableRandomDirection(current, visitMap)
                if (direction == DungeonPlace.Direction.NONE && !isComplete(way, roomCount)) {
                    moveBack(current, visitMap, way)
                    continue
                } else {
                    visit(current, direction, visitMap, way)
                }
            }

            printWays(way)
            connectRooms(start, way)

            return if (0 < repeatCount && 2 < way.size) {
                val newStart = Position(entrance?.position?.x ?: 0, entrance?.position?.y ?: 0)
                val somePoint = Random.nextInt(way.size / 2)
                for (i in 0 until somePoint) {
                    newStart.moveDirection(way[i])
                }
                makeRooms(roomCount, newStart, visitMap, repeatCount - 1)
            } else {
                isComplete(way, roomCount)
            }
        }

        private fun availableRandomDirection(
            current: Position,
            visitMap: VisitMap
        ): DungeonPlace.Direction {
            val x = current.x
            val y = current.y
            val directions = ArrayList<DungeonPlace.Direction>()
            if (visitMap.canVisit(x + 1, y)) directions.add(DungeonPlace.Direction.EAST)
            if (visitMap.canVisit(x - 1, y)) directions.add(DungeonPlace.Direction.WEST)
            if (visitMap.canVisit(x, y + 1)) directions.add(DungeonPlace.Direction.SOUTH)
            if (visitMap.canVisit(x, y - 1)) directions.add(DungeonPlace.Direction.NORTH)

            return if (directions.isEmpty()) {
                DungeonPlace.Direction.NONE
            } else {
                directions.random()
            }
        }

        private fun isComplete(way: ArrayList<DungeonPlace.Direction>, roomCount: Int): Boolean {
            return (way.size == roomCount)
        }

        private fun moveBack(
            current: Position,
            visitMap: VisitMap,
            way: ArrayList<DungeonPlace.Direction>
        ) {
            if (way.isEmpty()) {
                return
            }
            val x = current.x
            val y = current.y
            val lastDirection = way.removeAt(way.size - 1)
            visitMap.visitBack(x, y)
            current.moveDirection(lastDirection.getOpposite())

            println("move back : ($x, $y) -> (${current.x}, ${current.y}) ")
        }

        private fun visit(
            current: Position,
            direction: DungeonPlace.Direction,
            visitMap: VisitMap,
            way: ArrayList<DungeonPlace.Direction>
        ) {
            val x = current.x
            val y = current.y
            way.add(direction)
            current.moveDirection(direction)
            visitMap.visit(current.x, current.y)

            println("visit : ($x, $y) -> (${current.x}, ${current.y})")
        }
    }
}
