package com.zs.mol.model.dungeon.dice

abstract class DiceCheck<T>() {
    abstract fun check(condition: T): Boolean
}