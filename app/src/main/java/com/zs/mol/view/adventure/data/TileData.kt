package com.zs.mol.view.adventure.data

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory

class TileData(private val tileType: Int, private val resourceId: Int, context: Context) {
    val bitmap: Bitmap by lazy {
        val raw = BitmapFactory.decodeResource(context.resources, resourceId)
        Bitmap.createScaledBitmap(raw, tileWidth, tileHeight, true).apply {
            raw.recycle()
        }
    }

    companion object {
        const val TYPE_SHEEP = 0
        const val TYPE_WOOD = 1
        const val TYPE_DESERT = 2
        const val TYPE_WATER = 3
        const val TYPE_ROCK = 4
        const val TYPE_WHEAT = 5
        const val TYPE_CLAY = 6

        const val tileWidth = 55 * 3
        const val tileHeight = 64 * 3
    }
}