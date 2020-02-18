package com.zs.mol.model.user

import com.zs.mol.model.battle.unit.BaseUnit

data class GameData(val userId: String) {
    val units = ArrayList<BaseUnit>()
}