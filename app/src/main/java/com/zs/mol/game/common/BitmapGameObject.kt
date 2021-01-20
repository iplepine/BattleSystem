package com.zs.mol.game.common

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint

open class BitmapGameObject(
    var bitmap: Bitmap? = null,
    val layer: Int = DrawingLayer.DEFAULT
) : GameObject() {

    var width = 0
    var height = 0

    open fun draw(canvas: Canvas, paint: Paint) {
        bitmap?.also {
            canvas.drawBitmap(it, position.x, position.y, paint)
        }
    }

}