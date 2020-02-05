package com.zs.battlesystem.data.battle.skill.active.continuous.debuff

class Hold : Debuff() {
    init {
        name = "Hold"

        coolTime = 30000L
        effectTime = 10000L
        afterDelay = 3000L
    }
}