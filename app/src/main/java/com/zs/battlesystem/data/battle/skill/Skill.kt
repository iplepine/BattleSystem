package com.zs.battlesystem.data.battle.skill

import com.zs.battlesystem.data.battle.event.BattleEvent
import com.zs.battlesystem.data.battle.unit.BattleUnit
import io.reactivex.subjects.PublishSubject

open abstract class Skill {
    var name: String = ""
    var description: String = ""
    var castingTime: Long = 0L
    var effectTime: Long = 0L
    var delay: Long = 0L

    open fun onEffect(
        user: BattleUnit,
        targets: Array<BattleUnit>,
        eventSubject: PublishSubject<BattleEvent>
    ) {
    }
}