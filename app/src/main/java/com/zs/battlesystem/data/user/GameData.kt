package com.zs.battlesystem.data.user

import com.zs.battlesystem.data.battle.unit.BaseUnit

data class GameData(val userId: String) {
    val units = ArrayList<BaseUnit>()
}