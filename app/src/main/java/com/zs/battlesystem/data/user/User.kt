package com.zs.battlesystem.data.user

object User {
    val id = "mine"

    fun isMine(id: String): Boolean {
        return this.id == id
    }
}