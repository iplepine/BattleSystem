package com.zs.battlesystem.data.battle.skill

import com.zs.battlesystem.data.battle.unit.BaseUnit

open class BaseSkill(val name: String) {
    fun onEffect(user: BaseUnit, targets: Array<BaseUnit>) {

    }
}