package com.zs.mol.model.user

class UserStatus(var level: Int = 1, var exp: Long = 0, var gold: Long = 0) {
    fun addExp(amount: Long) {
        exp += amount
    }

    fun reduceExp(amount: Long) {
        exp -= amount
    }

    fun gainGold(amount: Long) {
        gold += amount
    }

    fun useGold(amount: Long): Boolean {
        return if (gold < amount) {
            false
        } else {
            gold -= amount
            true
        }
    }

    fun reduceGold(amount: Long, useDebt: Boolean = false) {
        gold -= amount
        if (gold < 0 && !useDebt) {
            gold = 0
        }
    }
}