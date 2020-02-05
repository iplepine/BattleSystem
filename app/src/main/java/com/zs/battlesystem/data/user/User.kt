package com.zs.battlesystem.data.user

import com.zs.battlesystem.data.battle.unit.BaseUnit

object User {
    val id = "mine"

    var level = 1
    var gold: Long = 0

    val units = ArrayList<BaseUnit>()

    fun isMine(id: String): Boolean {
        return this.id == id
    }
}
