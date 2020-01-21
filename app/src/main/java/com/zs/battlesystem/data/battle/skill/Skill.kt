package com.zs.battlesystem.data.battle.skill

import com.zs.battlesystem.data.event.BaseEvent
import com.zs.battlesystem.data.battle.unit.BattleUnit
import io.reactivex.subjects.PublishSubject

open abstract class Skill {
    var name: String = ""
    var description: String = ""

    var castingTime: Long = 0L
    var effectTime: Long = 0L
    var afterDelay: Long = 0L

    var coolTime = 0L   // 스킬의 쿨타임
    var coolDown = 0L   // 남은 쿨 타임

    open fun onEffect(
        user: BattleUnit,
        targets: ArrayList<BattleUnit>,
        eventSubject: PublishSubject<BaseEvent>
    ) {
    }

    open fun getExpectEffect(): Double {
        return 0.0
    }
}