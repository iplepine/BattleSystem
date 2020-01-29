package com.zs.battlesystem.data.battle.unit

import com.zs.battlesystem.data.battle.Battle
import com.zs.battlesystem.data.battle.controller.DefaultBattleUnitController
import com.zs.battlesystem.data.battle.skill.Skill
import com.zs.battlesystem.data.battle.unit.stat.UnitState
import com.zs.battlesystem.data.common.Logger
import com.zs.battlesystem.data.common.MonoBehaviour
import io.reactivex.subjects.PublishSubject
import java.util.*

class BattleUnit(val base: BaseUnit) : MonoBehaviour() {
    companion object {
        const val DEFAULT_DELAY = 1000L
    }

    val id: String = UUID.randomUUID().toString()
    var owner = "enemy"

    var state = State(UnitState.IDLE)

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
        return state.name == UnitState.DIE
    }

    fun isAfterDelay(): Boolean {
        return state.name == UnitState.AFTER_DELAY
    }

    private fun isIdle(): Boolean {
        return state.name == UnitState.IDLE
    }

    fun canAction(): Boolean {
        return isIdle() && state.isEnd()
    }

    override fun updateTime(time: Long) {
        if (time <= 0) {
            return
        }

        val spentTime = state.spendTime(time)
        timePast(spentTime)
        updateState()

        if (spentTime < time) {
            updateTime(time - spentTime)
        }
    }

    private fun timePast(time: Long) {
        base.skills.forEach { it.reduceCoolDown(time) }
    }

    private fun updateState() {
        if (state.isEnd()) {
            when (state.name) {
                UnitState.DIE -> return
                UnitState.CASTING -> onFinishCasting()
                UnitState.EFFECT -> onFinishEffect()
                UnitState.AFTER_DELAY -> onFinishAfterDelay()
                UnitState.STUN -> ready(0)
            }
        }
    }

    private fun changeState(state: State) {
        this.state = state
    }

    fun changeState(stateName: String) {
        this.state = State(stateName)
    }

    fun startCasting(
        skill: Skill,
        target: List<BattleUnit>,
        messageSubject: PublishSubject<String>? = null
    ) {
        Logger.d("${base.name} start casting [${skill.name}]")
        castingSkill = ReservedSkill(skill, target, messageSubject)
        changeState(State(UnitState.CASTING, castingSkill?.skill?.castingTime ?: DEFAULT_DELAY))
        if (state.isEnd()) {
            updateState()
        }
    }

    fun useSkillImmediate(
        skill: Skill,
        target: List<BattleUnit>,
        messageSubject: PublishSubject<String>? = null
    ) {
        skill.onEffect(this, target, messageSubject)
        Logger.d("${base.name} use [${skill.name}] immediately")
    }

    private fun onFinishCasting() {
        Logger.d("${base.name} finish casting on [${castingSkill?.skill?.name}]")
        startEffect()
    }

    private fun startEffect() {
        changeState(State(UnitState.EFFECT, castingSkill?.skill?.effectTime ?: DEFAULT_DELAY))
        if (state.isEnd()) {
            updateState()
        }
    }

    private fun onFinishEffect() {
        castingSkill?.also {
            it.skill.onEffect(this, it.target, it.messageSubject)
        }
        startAfterDelay()
    }

    private fun startAfterDelay() {
        changeState(State(UnitState.AFTER_DELAY, castingSkill?.skill?.afterDelay ?: DEFAULT_DELAY))
        if (state.isEnd()) {
            updateState()
        }
    }

    private fun onFinishAfterDelay() {
        castingSkill?.skill?.startCoolDown()
        castingSkill = null
        ready(calculateTurnDelay())
    }

    private fun ready(delay: Long) {
        changeState(State(UnitState.IDLE, delay))
        Logger.d("${base.name} is waiting next turn!")
    }

    fun onStun(time: Long) {
        var delay = time
        if (isIdle()) {
            delay += state.delay
        }

        changeState(
            State(UnitState.STUN, delay)
        )
        castingSkill = null
    }

    fun onDamage(damage: Int) {
        if (damage < stat.hp) {
            stat.hp -= damage
        } else {
            stat.hp = 0
        }

        if (stat.hp <= 0) {
            changeState(State(UnitState.DIE))
        }
    }

    fun onTurn(battle: Battle) {
        Logger.d("${base.name}'s turn!")

        if (battleUnitController == null) {
            Logger.d("Unit Controller is null")
            battle.onFinishInput()
        } else {
            Logger.d("please, wait input\n")
            battleUnitController!!.controlUnit(this@BattleUnit, battle)
        }
    }

    private fun calculateTurnDelay(): Long {
        if (stat.speed == 0) {
            return 1000L
        }
        return 1000L
    }

    class ReservedSkill(
        var skill: Skill,
        var target: List<BattleUnit>,
        var messageSubject: PublishSubject<String>? = null
    )

    class State(val name: String, var delay: Long = 0L) {
        /**
         * 사용한 시간 리턴
         */
        fun spendTime(time: Long): Long {
            val spentTime = if (time < delay) {
                delay - time
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