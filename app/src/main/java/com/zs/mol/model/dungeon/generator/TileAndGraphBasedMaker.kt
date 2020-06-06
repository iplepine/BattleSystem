package com.zs.mol.model.dungeon.generator

import com.zs.mol.model.common.Position
import com.zs.mol.model.dungeon.Dungeon
import com.zs.mol.model.dungeon.Dungeon.DungeonMap.TileType
import com.zs.mol.model.dungeon.DungeonPlace
import kotlin.random.Random

class TileAndGraphBasedMaker(private val width: Int, private val height: Int) :
    MapGenerator() {

    var entrance = TiledPlace(Random.nextInt(width), Random.nextInt(height), TileType.ENTRANCE)
    val map: Dungeon.DungeonMap = Dungeon.DungeonMap(width, height)
    val dungeon: TiledDungeon = TiledDungeon(entrance, map)

    var visitMap = VisitMap(width, height)

    val currentPosition = Position(entrance.position.x, entrance.position.y)

    lateinit var direction: DungeonPlace.Direction

    init {
        visitMap.visit(currentPosition.x, currentPosition.y)
        entrance.apply {
            map.set(currentPosition.x, currentPosition.y, tileType)
            dungeon.addPlace(entrance)
        }
    }

    override fun createMap(): Array<IntArray> {
        return Array(width * 2 - 1) { IntArray(height * 2 - 1) { TileType.WALL } }.also { ret ->
            for (i in 0 until map.height) {
                for (j in 0 until map.width) {
                    ret[i * 2][j * 2] = map.get(i, j)
                }
            }

            dungeon.places.apply {
                keys.forEach { key ->
                    val place = get(key)
                    place?.also { place ->
                        if (place.connects.containsKey(DungeonPlace.Direction.EAST)) {
                            ret[place.position.x * 2 + 1][place.position.y * 2] =
                                TileType.HORIZONTAL_WAY
                        }

                        if (place.connects.containsKey(DungeonPlace.Direction.SOUTH)) {
                            ret[place.position.x * 2][place.position.y * 2 + 1] =
                                TileType.VERTICAL_WAY
                        }
                    }
                }
            }
        }
    }

    fun initStartPosition(x: Int = Random.nextInt(width), y: Int = Random.nextInt(height)) {
        entrance = TiledPlace(x, y, TileType.ENTRANCE)
    }

    fun makeRooms(roomCount: Int, generation: Int) {
        var madeCount = 0
        var tryCount = 0
        val TRY_LIMIT = 1000

        while (madeCount < roomCount && tryCount++ < TRY_LIMIT) {
            val directionList = DungeonPlace.Direction.values()
                .filter { it != DungeonPlace.Direction.NONE }
                .toList()
                .shuffled()

            for (i in directionList.indices) {
                val d = directionList[i]
                if (makeRoom(d, generation)) {
                    madeCount++
                    break
                }
            }
        }
    }

    fun makeRoom(direction: DungeonPlace.Direction, generation: Int): Boolean {
        var x = currentPosition.x
        var y = currentPosition.y

        val currentRoom = dungeon.getPlace(x, y)

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
        if (visitMap.isVisit(x, y) || map.get(x, y) != TileType.WALL) {
            moveBack()
            return false
        }

        // 더이상 확장 할 수 없는 경우
        if (!canSpread(map, x, y)) {
            moveBack()
            return false
        }

        visitMap.visit(x, y)
        map.set(x, y, TileType.GROUND)

        val newPlace = dungeon.getPlace(x, y)
        dungeon.connectPlace(currentRoom, direction, newPlace)

        return true
    }

    private fun canSpread(map: Dungeon.DungeonMap, x: Int, y: Int): Boolean {
        return (x + 1 < width && !visitMap.isVisit(x + 1, y) && map.get(x + 1, y) == TileType.WALL)
                || (0 <= x - 1 && !visitMap.isVisit(x - 1, y) && map.get(
            x - 1,
            y
        ) == TileType.WALL)
                || (y + 1 < height && !visitMap.isVisit(x, y + 1) && map.get(
            x,
            y + 1
        ) == TileType.WALL)
                || (0 <= y - 1 && !visitMap.isVisit(x, y - 1) && map.get(
            x,
            y - 1
        ) == TileType.WALL)
    }

    private fun moveBack() {
        when (direction) {
            DungeonPlace.Direction.NORTH -> currentPosition.up()
            DungeonPlace.Direction.EAST -> currentPosition.right()
            DungeonPlace.Direction.SOUTH -> currentPosition.down()
            DungeonPlace.Direction.WEST -> currentPosition.left()
        }
    }

    class TiledPlace(x: Int, y: Int, var tileType: Int = TileType.GROUND) :
        DungeonPlace() {

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

    class TiledDungeon(start: TiledPlace, val map: DungeonMap) : Dungeon<TiledPlace>(start) {
        fun getPlace(x: Int, y: Int): TiledPlace {
            return getPlace(TiledPlace.createHashKey(x, y)) ?: TiledPlace(x, y)
        }
    }

    class VisitMap(width: Int, height: Int) {
        private val map = Array(height) { BooleanArray(width) { false } }

        fun isVisit(x: Int, y: Int): Boolean {
            return map[y][x]
        }

        fun visit(x: Int, y: Int) {
            map[y][x] = true
        }
    }
}
