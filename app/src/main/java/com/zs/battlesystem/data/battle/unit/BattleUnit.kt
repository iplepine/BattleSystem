package com.zs.battlesystem.data.battle.unit

import com.zs.battlesystem.data.battle.Battle
import com.zs.battlesystem.data.battle.controller.DefaultBattleUnitController
import com.zs.battlesystem.data.battle.skill.Skill
import com.zs.battlesystem.data.battle.unit.stat.UnitState
import com.zs.battlesystem.data.common.GameObject
import com.zs.battlesystem.data.common.Logger
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

    var baseStat = base.baseStat.copy()
    var stat = base.stat.copy()

    var castingSkill: ReservedSkill? = null

    var battleUnitController: DefaultBattleUnitController? = DefaultBattleUnitController

    fun isMine(id: String): Boolean {
        return owner == id
    }

    fun isEnemy(id: String): Boolean {
        return owner != id
    }

    fun isDie(): Boolean {
        return state == UnitState.DIE
    }

    fun isReady(): Boolean {
        return state == UnitState.READY
    }

    override fun updateTime(time: Long) {
        turnDelay = max(0, turnDelay - time)
        stateDelay = max(0, stateDelay - time)

        while (turnDelay <= 0) {
            if (stateDelay <= 0) {
                when (state) {
                    UnitState.DIE -> return
                    UnitState.CASTING -> onFinishCasting()
                    UnitState.EFFECT -> onFinishEffect()
                    UnitState.AFTER_DELAY -> onFinishAfterDelay()
                    UnitState.STUN -> ready()
                    else -> ready()
                }
            }
        }
    }

    fun startCasting(
        skill: Skill,
        target: List<BattleUnit>,
        messageSubject: PublishSubject<String>? = null
    ) {
        castingSkill = ReservedSkill(skill, target, messageSubject)
        state = UnitState.CASTING
        stateDelay = castingSkill?.skill?.castingTime ?: DEFAULT_DELAY
    }

    fun useSkillImmediate(
        skill: Skill,
        target: List<BattleUnit>,
        messageSubject: PublishSubject<String>? = null
    ) {
        skill.onEffect(this, target, messageSubject)
    }

    private fun onFinishCasting() {
        startEffect()
    }

    private fun startEffect() {
        state = UnitState.EFFECT
        stateDelay = castingSkill?.skill?.effectTime ?: DEFAULT_DELAY
    }

    private fun onFinishEffect() {
        castingSkill?.also {
            it.skill.onEffect(this, it.target, it.messageSubject)
        }
        startAfterDelay()
    }

    private fun startAfterDelay() {
        state = UnitState.AFTER_DELAY
        stateDelay = castingSkill?.skill?.afterDelay ?: DEFAULT_DELAY
    }

    private fun onFinishAfterDelay() {
        castingSkill = null
        ready()
    }

    fun ready() {
        state = UnitState.READY
    }

    fun onStun(time: Long) {
        state = UnitState.STUN
        turnDelay += time
        castingSkill = null
    }

    fun onDamage(damage: Int) {
        if (damage < stat.hp) {
            stat.hp -= damage
        } else {
            stat.hp = 0
        }

        if (stat.hp <= 0) {
            state = UnitState.DIE
        }
    }

    fun onTurn(battle: Battle) {
        Logger.d("${base.name}'s turn!")

        if (battleUnitController == null) {
            Logger.d("Unit Controller is null")
            return
        }

        Thread.sleep(1000)
        battleUnitController?.onReceiveUnitControl(this@BattleUnit, battle)
        onFinishTurn()
    }

    fun onFinishTurn() {
    }

    class ReservedSkill(
        var skill: Skill,
        var target: List<BattleUnit>,
        var messageSubject: PublishSubject<String>? = null
    )
}