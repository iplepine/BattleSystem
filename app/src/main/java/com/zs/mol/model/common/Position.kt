package com.zs.mol.model.common

class Position(var x: Int = 0, var y: Int = 0) {
    override fun toString(): String {
        return "$x,$y"
    }

    fun down() {
        y += 1
    }

    fun up() {
        y -= 1
    }

    fun right() {
        x += 1
    }

    fun left() {
        x -= 1
    }
}