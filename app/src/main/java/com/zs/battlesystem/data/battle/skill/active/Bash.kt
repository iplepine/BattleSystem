package com.zs.battlesystem.data.battle.skill.active

import com.zs.battlesystem.data.battle.BattleFunction
import com.zs.battlesystem.data.battle.event.BattleEvent
import com.zs.battlesystem.data.battle.event.SkillEvent
import com.zs.battlesystem.data.battle.skill.Skill
import com.zs.battlesystem.data.battle.unit.BattleUnit
import io.reactivex.subjects.PublishSubject

object Bash : Skill() {
    private const val damageRatio = 2.0

    init {
        name = "강타"
        description = "힘을 모아 강하게 내려 친다"
        castingTime = 0
        effectTime = 0
        delay = 0
    }

    override fun onEffect(
        user: BattleUnit,
        targets: Array<BattleUnit>,
        eventSubject: PublishSubject<BattleEvent>
    ) {
        require(targets.isNotEmpty())

        val battleEvent = SkillEvent()
        val target = targets[0]

        if (BattleFunction.checkEvade(user, target)) {
            battleEvent.data.putBoolean(SkillEvent.Result.EVADE, true)

            eventSubject.onNext(
                BattleEvent(
                    BattleEvent.Type.SKILL,
                    "공격을 회피하였습니다."
                )
            )
            return
        }

        var attack = BattleFunction.getDefaultAttackDamage(user) * damageRatio
        if (BattleFunction.checkCritical(user, target)) {
            attack *= 2
            battleEvent.data.putBoolean(SkillEvent.Result.CRITICAL, true)
        }

        val defence = target.base.battleStat.def
        val damage = if (defence < attack) {
            attack - defence
        } else {
            attack * (1 - BattleFunction.getDamageReductionRatio(attack.toInt(), defence))
            battleEvent.data.putBoolean(SkillEvent.Result.BLOCK, true)
        }

        eventSubject.onNext(battleEvent as BattleEvent)
    }
}