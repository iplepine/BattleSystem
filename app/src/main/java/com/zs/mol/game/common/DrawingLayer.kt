package com.zs.mol.game.common

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import com.zs.mol.model.consts.Screen

class DrawingLayer(val useCamera: Boolean = true) {
    private val bitmap: Bitmap = Bitmap.createBitmap(Screen.width, Screen.height, Bitmap.Config.ARGB_4444)
    private val gameObjects: ArrayList<BitmapGameObject> = ArrayList()

    private val paint = Paint().apply {
        isAntiAlias = true
        isFilterBitmap = true
        isDither = true
    }

    fun draw(canvas: Canvas, camera: Camera? = null) {
        val bitmapCanvas = Canvas(bitmap)
        gameObjects.forEach{
            val obj = (it)
            if (obj.hasChanged) {
                obj.draw(bitmapCanvas, paint)
                obj.hasChanged = false
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
        const val BACKGROUND = -1
        const val MAP = 0
    }
}
