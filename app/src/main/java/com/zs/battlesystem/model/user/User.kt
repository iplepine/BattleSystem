package com.zs.battlesystem.model.user

import com.zs.battlesystem.model.battle.unit.BaseUnit

object User {
    val id = "mine"

    var level = 1
    var gold: Long = 0

    val units = ArrayList<BaseUnit>()

    fun isMine(id: String): Boolean {
        return this.id == id
    }
}
