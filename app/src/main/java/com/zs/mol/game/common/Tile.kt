package com.zs.mol.game.common

import com.zs.mol.view.adventure.data.TileData

class Tile(var data: TileData): BitmapGameObject(data.bitmap, DrawingLayer.MAP)
