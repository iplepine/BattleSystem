package com.zs.mol.game.common

abstract class GameObject {
    var position = PositionF()

    open fun move(x: Float, y: Float) {
        position.x += x
        position.y += y
    }

    open fun setPosition(x: Float, y: Float) {
        position.x = x
        position.y = y
    }
}