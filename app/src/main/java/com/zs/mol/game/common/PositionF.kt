package com.zs.mol.game.common

class PositionF(var x: Float = 0f, var y: Float = 0f) {
    override fun toString(): String {
        return "($x,$y)"
    }

    override fun equals(other: Any?): Boolean {
        if (other is PositionF) {
            return (other.x == x && other.y == y)
        }
        return false
    }
}