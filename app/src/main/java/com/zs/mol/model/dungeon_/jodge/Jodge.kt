package com.zs.mol.model.dungeon_.jodge

abstract class Jodge<T>() {
    abstract fun check(condition: T): Boolean
}