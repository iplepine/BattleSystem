package com.zs.mol.model.user

import com.zs.mol.model.battle.unit.BaseUnit

data class FirebaseSavable(val userId: String) {
    val units = ArrayList<BaseUnit>()
}