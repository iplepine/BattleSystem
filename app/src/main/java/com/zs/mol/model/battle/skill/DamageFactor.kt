package com.zs.mol.model.battle.skill

import com.zs.mol.model.battle.unit.BattleUnit

data class DamageFactor(
    val who: BattleUnit,
    val key: String,
    val amount: Double,
    val isFlat: Boolean
)