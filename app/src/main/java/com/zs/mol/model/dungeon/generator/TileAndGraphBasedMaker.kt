package com.zs.mol.model.dungeon.generator

import com.zs.mol.model.dungeon.Dungeon
import com.zs.mol.model.dungeon.DungeonPlace
import kotlin.random.Random

class TileAndGraphBasedMaker(private val width: Int, private val height: Int, val difficulty: Int) :
    MapGenerator() {

    var entrance = TiledRoom(Random.nextInt(width), Random.nextInt(height), FieldType.ENTRANCE)
    val map: Dungeon.DungeonMap = Dungeon.DungeonMap(width, height)
    val dungeon: TiledDungeon = TiledDungeon(entrance, map)

    var visitMap = VisitMap(width, height)

    var x = entrance.x
    var y = entrance.y

    lateinit var direction: DungeonPlace.Direction

    init {
        visitMap.visit(x, y)
        entrance.apply {
            map.set(x, y, fieldType)
            dungeon.addPlace(entrance)
        }
    }

    override fun createMap(): Array<IntArray> {
        return Array(width * 2) { IntArray(height * 2) { FieldType.WALL } }.also { ret ->
            makeRooms(difficulty)
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
                            ret[place.x * 2 + 1][place.y * 2] = FieldType.HORIZONTAL_WAY
                        }

                        if (place.connects.containsKey(DungeonPlace.Direction.SOUTH)) {
                            ret[place.x * 2][place.y * 2 + 1] = FieldType.VERTICAL_WAY
                        }
                    }
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
                    madeCount++
                    break
                }
            }
        }
    }

    private fun makeRoom(direction: DungeonPlace.Direction): Boolean {
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
        if (visitMap.isVisit(x, y) || map.get(x, y) != FieldType.WALL) {
            moveBack()
            return false
        }

        // 더이상 확장 할 수 없는 경우
        if (!canSpread(map, x, y)) {
            moveBack()
            return false
        }

        visitMap.visit(x, y)
        map.set(x, y, FieldType.GROUND)

        val newPlace = dungeon.getPlace(x, y)
        dungeon.connectPlace(currentRoom, direction, newPlace)

        return true
    }

    // TODO 여기 문제 있는 듯
    private fun canSpread(map: Dungeon.DungeonMap, x: Int, y: Int): Boolean {
        return (x + 1 < width && visitMap.isVisit(x + 1, y) || map.get(x + 1, y) != FieldType.WALL)
                || (0 <= x - 1 && visitMap.isVisit(x - 1, y) && map.get(x - 1, y) != FieldType.WALL)
                || (y + 1 < height && visitMap.isVisit(x, y + 1) && map.get(x,y + 1) != FieldType.WALL)
                || (0 <= y - 1 && visitMap.isVisit(x, y - 1) && map.get(x, y - 1) != FieldType.WALL)
    }

    private fun moveBack() {
        when (direction) {
            DungeonPlace.Direction.NORTH -> y += 1
            DungeonPlace.Direction.EAST -> x -= 1
            DungeonPlace.Direction.SOUTH -> y -= 1
            DungeonPlace.Direction.WEST -> x += 1
        }
    }

    class TiledRoom(var x: Int, var y: Int, var fieldType: Int = FieldType.GROUND) :
        DungeonPlace() {

        companion object {
            fun createHashKey(x: Int, y: Int): String {
                return "$x,$y"
            }

            fun getHashKey(place: TiledRoom): String {
                return createHashKey(place.x, place.y)
            }
        }

        override fun hashCode(): Int {
            return getHashKey(this).hashCode()
        }
    }

    class TiledDungeon(start: TiledRoom, val map: DungeonMap) : Dungeon<TiledRoom>(start) {
        fun getPlace(x: Int, y: Int): TiledRoom {
            return getPlace(TiledRoom.createHashKey(x, y)) ?: TiledRoom(x, y)
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
