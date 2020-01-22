package com.zs.battlesystem.data.battle.unit

import com.zs.battlesystem.data.battle.Battle
import com.zs.battlesystem.data.battle.controller.DefaultBattleUnitController
import com.zs.battlesystem.data.battle.skill.Skill
import com.zs.battlesystem.data.battle.unit.stat.UnitState
import com.zs.battlesystem.data.common.GameObject
import com.zs.battlesystem.data.common.Logger
import com.zs.battlesystem.data.user.User
import io.reactivex.subjects.PublishSubject
import java.util.*
import kotlin.math.max

class BattleUnit(val base: BaseUnit) : GameObject() {
    companion object {
        const val DEFAULT_DELAY = 1000L
    }

    val id: String = UUID.randomUUID().toString()
    var owner = "enemy"

    var turnDelay = 0L

    var stateDelay = 0L
    var state = UnitState.READY

    var reservedSkill: ReservedSkill? = null

    var battleUnitController: DefaultBattleUnitController? = DefaultBattleUnitController

    fun isMine(): Boolean {
        return User.isMine(owner)
    }

    fun isEnemy(): Boolean {
        return owner == "enemy"
    }

    fun isDie(): Boolean {
        return state == UnitState.DIE
    }

    override fun updateTime(time: Long) {
        turnDelay = max(0, turnDelay - time)
        stateDelay = max(0L, stateDelay - time)

        if (stateDelay <= 0) {
            when (state) {
                UnitState.CASTING -> onFinishCasting()
                UnitState.EFFECT -> onFinishEffect()
                UnitState.AFTER_DELAY -> onFinishAfterDelay()
                UnitState.STUN -> ready()
                else -> ready()
            }
        }
    }

    fun startCasting(
        skill: Skill,
        target: List<BattleUnit>,
        messageSubject: PublishSubject<String>? = null
    ) {
        reservedSkill = ReservedSkill(skill, target, messageSubject)
        state = UnitState.CASTING
        stateDelay = reservedSkill?.skill?.castingTime ?: DEFAULT_DELAY

        Logger.d("${base.name}이 ${skill.name}을 사용")
    }

    private fun onFinishCasting() {
        startEffect()
    }

    private fun startEffect() {
        state = UnitState.EFFECT
        stateDelay = reservedSkill?.skill?.effectTime ?: DEFAULT_DELAY
    }

    private fun onFinishEffect() {
        reservedSkill?.also {
            it.skill.onEffect(this, it.target, it.messageSubject)
        }
        startAfterDelay()
    }

    private fun startAfterDelay() {
        state = UnitState.AFTER_DELAY
        stateDelay = reservedSkill?.skill?.afterDelay ?: DEFAULT_DELAY
    }

    private fun onFinishAfterDelay() {
        ready()
    }

    fun ready() {
        state = UnitState.READY
    }

    fun onStun(time: Long) {
        state = UnitState.STUN
        turnDelay += time
        reservedSkill = null
    }

    fun onDamage(damage: Int) {
        if (damage < base.battleStat.hp) {
            base.battleStat.hp -= damage
        } else {
            base.battleStat.hp = 0
            state = UnitState.DIE
        }
    }

    fun onTurn(battle: Battle) {
        Logger.d("${base.name}의 턴!")

        if (battleUnitController == null) {
            Logger.d("Unit Controller 가 없습니다.")
            return
        }

        Thread.sleep(1000)
        battleUnitController?.onReceiveUnitControl(this@BattleUnit, battle)
    }

    class ReservedSkill(
        var skill: Skill,
        var target: List<BattleUnit>,
        var messageSubject: PublishSubject<String>? = null
    )
}