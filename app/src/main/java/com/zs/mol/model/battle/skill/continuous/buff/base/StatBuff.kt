package com.zs.mol.model.battle.skill.continuous.buff.base

class StatBuff(
    val key: String,
    val amount: Double,
    val isFlat: Boolean,
    duration: Long,
    effectDelay: Long = duration
) :
    Buff(
        duration,
        effectDelay
    )