package com.zs.mol.model.unit

import com.zs.mol.model.battle.Battle
import com.zs.mol.model.battle.BattleFunction
import com.zs.mol.model.battle.controller.DefaultBattleUnitController
import com.zs.mol.model.common.Logger
import com.zs.mol.model.common.MonoBehaviour
import com.zs.mol.model.skill.UnitSkill
import com.zs.mol.model.skill.continuous.StatusEffect
import com.zs.mol.model.skill.continuous.buff.base.Buff
import com.zs.mol.model.skill.continuous.buff.base.StatBuff
import com.zs.mol.model.skill.continuous.debuff.base.DeBuff
import com.zs.mol.model.stat.SecondStat.Companion.HP
import com.zs.mol.model.stat.SecondStat.Companion.MP
import com.zs.mol.model.stat.SecondStat.Companion.WILL
import com.zs.mol.model.stat.Stat
import com.zs.mol.model.stat.UnitState
import io.reactivex.subjects.PublishSubject
import java.util.*
import kotlin.collections.ArrayList

class BattleUnit(owner: String, id: String = UUID.randomUUID().toString()) : BaseUnit(owner, id),
    MonoBehaviour {

    companion object {
        const val DEFAULT_DELAY = 2000L
        const val MINIMUM_DELAY = 500L
    }

    val location = Location(0.0, 0.0)
    var state = State(UnitState.IDLE)

    val buffs = ArrayList<StatusEffect>()
    val deBuffs = ArrayList<StatusEffect>()

    var castingSkill: ReservedSkill? = null
    var currentStat: Stat = totalStat.deepCopy()

    var battleUnitController: DefaultBattleUnitController? = DefaultBattleUnitController

    fun addSkill(skill: UnitSkill) {
        skills.add(skill)
    }

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
        updateSkills(time)
        updateBuffs(time)
    }

    private fun updateSkills(time: Long) {
        skills.forEach { it.updateTime(time) }
    }

    private fun updateBuffs(time: Long) {
        val endBuffs = buffs.filter {
            it.updateTime(time)
            it.isEnd()
        }

        buffs.removeAll(endBuffs)
    }

    override fun updateStat() {
        super.updateStat()

        val hp = currentStat.secondStat.get(HP)
        val mp = currentStat.secondStat.get(MP)
        val will = currentStat.secondStat.get(WILL)
        currentStat = totalStat.deepCopy()
        currentStat.secondStat[HP] = hp
        currentStat.secondStat[MP] = mp
        currentStat.secondStat[WILL] = will

        val flatStat = Stat()
        val percentStat = Stat.createInitializedStat(1.0, 1.0)

        buffs.forEach { buff ->
            (buff as? StatBuff)?.also {
                if (it.isFlat) {
                    flatStat.addValue(buff.key, buff.amount)
                } else {
                    percentStat.addValue(buff.key, buff.amount)
                }
            }
        }

        currentStat.baseStat.plus(flatStat.baseStat)
        currentStat.secondStat.plus(flatStat.secondStat)
        currentStat.baseStat.times(percentStat.baseStat)
        currentStat.secondStat.times(percentStat.secondStat)
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
        skill: UnitSkill,
        target: List<BattleUnit>,
        messageSubject: PublishSubject<String>? = null
    ) {
        Logger.d("$name start casting [${skill.getName()}]")
        castingSkill = ReservedSkill(skill, target, messageSubject)
        changeState(State(UnitState.CASTING, castingSkill?.skill?.getCastingTime() ?: 0))
        if (state.isEnd()) {
            updateState()
        }
    }

    fun useSkillImmediate(
        skill: UnitSkill,
        target: List<BattleUnit>,
        messageSubject: PublishSubject<String>? = null
    ) {
        skill.onEffect(this, target, messageSubject)
        Logger.d("$name use [${skill.getName()}] immediately")
    }

    private fun onFinishCasting() {
        Logger.d("$name finish casting on [${castingSkill?.skill?.getName()}]")
        startEffect()
    }

    private fun startEffect() {
        changeState(State(UnitState.EFFECT, castingSkill?.skill?.getEffectTime() ?: 0))
        if (state.isEnd()) {
            updateState()
        }
    }

    private fun onFinishEffect() {
        castingSkill?.run {
            skill.onEffect(this@BattleUnit, target, messageSubject)
        }

        startAfterDelay()
    }

    private fun startAfterDelay() {
        changeState(State(UnitState.AFTER_DELAY, castingSkill?.skill?.getAfterDelay() ?: 0))
        if (state.isEnd()) {
            updateState()
        }
    }

    private fun onFinishAfterDelay() {
        castingSkill?.skill?.startCoolDown()
        castingSkill = null
        ready(BattleFunction.calculateUnitTurnDelay(this))
    }

    private fun ready(delay: Long) {
        changeState(State(UnitState.IDLE, delay))
        Logger.d("$name is waiting next turn!")
    }

    fun onStun(time: Long) {
        castingSkill?.apply {
            // TODO 스턴 걸리면 현재 시전중인 정신집중 스킬 제거 해야함
        }

        var delay = time
        if (isIdle()) {
            delay += state.delay
        }

        changeState(
            State(UnitState.STUN, delay)
        )
        castingSkill = null
    }

    fun adjustHp(hpAmount: Int) {
        if (totalStat.secondStat.get(HP) < hpAmount) {
            currentStat.secondStat[HP] = 0.0
        } else {
            currentStat.secondStat[HP] = (totalStat.secondStat[HP] ?: 0.0) + hpAmount
        }

        if (currentStat.secondStat[HP] <= 0.0) {
            currentStat.secondStat[HP] = 0.0
            changeState(State(UnitState.DIE))
            Logger.d("$name died")
        } else {
            Logger.d(
                "$name " +
                        "HP : ${currentStat.secondStat[HP].toInt()}/${totalStat.secondStat[HP].toInt()}\n"
            )
        }
    }

    fun addBuff(buff: Buff) {
        buffs.add(buff)
    }

    fun clearBuff(buff: Buff) {
        buffs.remove(buff)
    }

    fun addDeBuff(deBuff: DeBuff) {
        deBuffs.add(deBuff)
    }

    fun clearDeBuff(deBuff: DeBuff) {
        deBuffs.remove(deBuff)
    }

    fun onTurn(battle: Battle) {
        Logger.d("$name's turn!")

        if (battleUnitController == null) {
            Logger.d("Unit Controller is null")
            battle.onFinishInput()
        } else {
            //Logger.d("please, wait input\n")
            battleUnitController!!.controlUnit(this@BattleUnit, battle)
        }
    }

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

    class Location(val x: Double, val y: Double)
}
