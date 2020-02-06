package com.zs.battlesystem.data.battle.skill.continuous.debuff.base

class StatDeBuff(
    val key: String,
    val amount: Double,
    val isFlat: Boolean,
    duration: Long,
    effectDelay: Long = duration
) :
    DeBuff(
        duration,
        effectDelay
    )