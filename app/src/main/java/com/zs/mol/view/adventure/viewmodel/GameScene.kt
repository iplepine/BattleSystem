package com.zs.mol.view.adventure.viewmodel

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.util.Log
import android.view.MotionEvent
import androidx.lifecycle.ViewModel
import com.zs.mol.R
import com.zs.mol.game.common.*
import com.zs.mol.model.common.MonoBehaviour
import com.zs.mol.model.consts.Screen.TOUCH_IGNORE
import com.zs.mol.view.adventure.data.TileData
import com.zs.mol.view.adventure.data.TileData.Companion.TYPE_CLAY
import com.zs.mol.view.adventure.data.TileData.Companion.TYPE_DESERT
import com.zs.mol.view.adventure.data.TileData.Companion.TYPE_ROCK
import com.zs.mol.view.adventure.data.TileData.Companion.TYPE_SHEEP
import com.zs.mol.view.adventure.data.TileData.Companion.TYPE_WATER
import com.zs.mol.view.adventure.data.TileData.Companion.TYPE_WHEAT
import com.zs.mol.view.adventure.data.TileData.Companion.TYPE_WOOD
import javax.inject.Inject
import kotlin.math.abs
import kotlin.random.Random

class GameScene @Inject constructor(
    context: Context
) : ViewModel() {
    private val drawingLayers = HashMap<Int, DrawingLayer>()
    private val camera = Camera()

    private val mapWidth = 10
    private val mapHeight = 10

    private val monoBehaviours = ArrayList<MonoBehaviour>()

    private val tileData = ArrayList<TileData>().apply {
        add(TileData(TYPE_SHEEP, R.drawable.tile_sheep, context))
        add(TileData(TYPE_WOOD, R.drawable.tile_wood, context))
        add(TileData(TYPE_DESERT, R.drawable.tile_desert, context))
        add(TileData(TYPE_WATER, R.drawable.tile_water, context))
        add(TileData(TYPE_ROCK, R.drawable.tile_rock, context))
        add(TileData(TYPE_WHEAT, R.drawable.tile_wheat, context))
        add(TileData(TYPE_CLAY, R.drawable.tile_clay, context))
    }

    val map = Array(mapHeight) { IntArray(mapWidth) }.also {
        for (i in 0 until mapHeight) {
            for (j in 0 until mapWidth) {
                it[i][j] = Random.nextInt(TYPE_CLAY)
            }
        }
    }

    private val player = Player(context.resources)

    private var previousX = 0f
    private var previousY = 0f

    init {
        initDrawingLayer()
        initBackground(context)
        initTiles()
        initPlayer()
    }

    private fun initDrawingLayer() {
        drawingLayers[DrawingLayer.BACKGROUND] = DrawingLayer(false)
        drawingLayers[DrawingLayer.MAP] = DrawingLayer(true)
        drawingLayers[DrawingLayer.DEFAULT] = DrawingLayer(true)
        drawingLayers[DrawingLayer.CHARACTER] = DrawingLayer(true)
    }

    private fun initBackground(context: Context) {
        val bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.wood10)
        val background = BitmapGameObject(bitmap, DrawingLayer.BACKGROUND)
        addDrawableObject(background)
    }

    private fun initTiles() {
        val tiles = Array(mapHeight) {
            Array(mapWidth) { Tile(tileData.random()) }
        }
        map.forEachIndexed { y, ints ->
            ints.forEachIndexed { x, tileType ->
                tiles[x][y].apply {
                    data = tileData[tileType]
                    position = getTilePosition(x, y)

                    addDrawableObject(this)
                }
            }
        }
    }

    private fun initPlayer() {
        player.position = getTilePosition(4, 4)
        addDrawableObject(player)
        monoBehaviours.add(player)
    }

    fun update(canvas: Canvas, time: Long) {
        monoBehaviours.forEach {
            it.updateTime(time)
        }

        drawingLayers.forEach {
            it.value.apply {
                if (useCamera) {
                    draw(canvas, camera)
                } else {
                    draw(canvas)
                }
            }
        }
    }

    private fun addDrawableObject(obj: BitmapGameObject) {
        val layer = obj.layer
        if (drawingLayers.contains(layer)) {
            drawingLayers[layer]?.addObject(obj)
        } else {
            drawingLayers[layer] = DrawingLayer().apply {
                addObject(obj)
            }
        }
    }

    private fun getTilePosition(x: Int, y: Int): PositionF {
        var positionX = x * TileData.tileWidth.toFloat()
        var positionY = y * TileData.tileHeight.toFloat()

        if (y % 2 == 1) {
            positionX += TileData.tileWidth / 2
        }

        if (y > 0) {
            positionY -= TileData.tileHeight / 4 * y
        }

        return PositionF(positionX, positionY)
    }

    fun moveCamera(x: Float, y: Float) {
        camera.position.x += x
        camera.position.y += y
    }

    fun onTouchEvent(event: MotionEvent): Boolean {
        Log.d("event x, y", "${event.rawX}, ${event.rawY}")

        if (event.action == MotionEvent.ACTION_DOWN) {
            previousX = event.rawX
            previousY = event.rawY
        }
        if (event.action == MotionEvent.ACTION_MOVE) {
            moveCamera(previousX - event.rawX, previousY - event.rawY)
            previousX = event.rawX
            previousY = event.rawY
        }

        if (event.action == MotionEvent.ACTION_UP) {
            if (abs(previousX - event.rawX) < TOUCH_IGNORE && abs(previousY - event.rawY) < TOUCH_IGNORE) {
                player.moveTo.x = event.rawX
                player.moveTo.y = event.rawY
            }
        }

        return true
    }
}
