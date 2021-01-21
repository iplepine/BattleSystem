package com.zs.mol.game.common

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.zs.mol.R

class TileMap(resource: Resources) : BitmapGameObject(null, DrawingLayer.CHARACTER) {

    init {
        width = 120
        height = 120

        bitmap = let {
            val original =
                BitmapFactory.decodeResource(resource, R.drawable.char_face_cultist_1)
            Bitmap.createScaledBitmap(original, width, height, true)
        }
    }
}