package com.zs.battlesystem.data.battle.skill.buff.base

import com.zs.battlesystem.data.battle.skill.Skill
import com.zs.battlesystem.data.battle.unit.BattleUnit
import io.reactivex.subjects.PublishSubject

abstract class Buff : Skill() {
    var duration: Long = 0L
    var remainingTime: Long = 0L

    init {
        targetType = TargetType.ALLIES
    }

    override fun onEffect(
        user: BattleUnit,
        target: BattleUnit,
        messageSubject: PublishSubject<String>?
    ) {
        target.buffs.add(this)
        remainingTime = duration
    }

    override fun updateTime(time: Long) {
        super.updateTime(time)
        remainingTime -= time
    }

    fun isEnd(): Boolean {
        return remainingTime <= 0
    }
}