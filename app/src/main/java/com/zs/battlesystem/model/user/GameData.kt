package com.zs.battlesystem.model.user

import com.zs.battlesystem.model.battle.unit.BaseUnit

data class GameData(val userId: String) {
    val units = ArrayList<BaseUnit>()
}