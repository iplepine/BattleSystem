package com.zs.mol.model.user

class UserStatus(var level: Int = 1, var exp: Long = 0) {
    fun addExp(amount: Long) {
        exp += amount
    }

    fun reduceExp(amount: Long) {
        exp -= amount
    }
}