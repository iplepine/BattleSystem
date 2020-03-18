package com.zs.mol.model.battle

import com.zs.mol.model.battle.controller.DefaultBattleUnitController
import com.zs.mol.model.skill.UnitSkill
import com.zs.mol.model.stat.UnitBattleState
import com.zs.mol.model.unit.BattleUnit
import io.reactivex.subjects.PublishSubject

class BattleStatus {
    var state = State(UnitBattleState.IDLE)
    var castingSkill: ReservedSkill? = null
    var battleUnitController: DefaultBattleUnitController? = DefaultBattleUnitController

    class ReservedSkill(
        var skill: UnitSkill,
        var target: List<BattleUnit>,
        var messageSubject: PublishSubject<String>? = null
    )

    class State(val name: String, var delay: Long = 0L) {
        /**
         * 사용한 시간 리턴
         */
        fun spendTime(time: Long): Long {
            val spentTime = if (time < delay) {
                time
            } else {
                delay
            }

            // 상태 변경이 되지 않는 조건
            if (spentTime <= 0) {
                return time
            }

            delay -= spentTime
            return spentTime
        }

        fun isEnd(): Boolean {
            return delay <= 0
        }
    }
}