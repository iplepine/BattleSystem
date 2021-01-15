package com.zs.mol.game.common

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint

open class BitmapGameObject(val bitmap: Bitmap, val layer: Int): GameObject() {
    var hasChanged = true

    open fun draw(canvas: Canvas, paint: Paint) {
        canvas.drawBitmap(bitmap, position.x, position.y, paint)
    }

    override fun move(x: Float, y: Float) {
        super.move(x, y)
        hasChanged = true
    }

    override fun setPosition(x: Float, y: Float) {
        super.setPosition(x, y)
        hasChanged = true
    }
}