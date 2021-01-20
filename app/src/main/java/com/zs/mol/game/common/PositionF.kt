package com.zs.mol.game.common

class PositionF(var x: Float = 0f, var y: Float = 0f) {

    constructor(position: PositionF) : this(position.x, position.y)

    override fun toString(): String {
        return "($x,$y)"
    }

    override fun equals(other: Any?): Boolean {
        if (other is PositionF) {
            return (x == other.x && y == other.y)
        }
        return false
    }
}