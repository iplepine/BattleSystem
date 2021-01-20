package com.zs.mol.game.common

import android.graphics.*
import com.zs.mol.model.consts.Screen

class DrawingLayer(val useCamera: Boolean = true) {
    private val bitmap: Bitmap =
        Bitmap.createBitmap(Screen.WIDTH, Screen.HEIGHT, Bitmap.Config.ARGB_4444)
    private val gameObjects: ArrayList<BitmapGameObject> = ArrayList()

    private val paint = Paint().apply {
        isAntiAlias = true
        isFilterBitmap = true
        isDither = true
    }

    fun draw(canvas: Canvas, camera: Camera? = null) {
        val bitmapCanvas = Canvas(bitmap)
        if (gameObjects.any { it.hasChanged }) {
            bitmapCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)
            gameObjects.forEach {
                it.draw(bitmapCanvas, paint)
                it.hasChanged = false
            }
        }

        if (camera == null) {
            canvas.drawBitmap(bitmap, 0f, 0f, paint)
        } else {
            canvas.drawBitmap(bitmap, -camera.position.x, -camera.position.y, paint)
        }
    }

    fun addObject(obj: BitmapGameObject) {
        gameObjects.add(obj)
    }

    companion object Layer {
        const val BACKGROUND = -2
        const val MAP = 1
        const val DEFAULT = 0
        const val CHARACTER = 2
    }
}
