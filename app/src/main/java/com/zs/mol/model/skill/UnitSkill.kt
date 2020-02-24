package com.zs.mol.model.skill

import com.zs.mol.model.manager.SkillManager
import com.zs.mol.model.unit.BattleUnit
import io.reactivex.subjects.PublishSubject

class UnitSkill(val id: Int, val status: SkillStatus = SkillStatus()) {

    fun getSkill(): Skill {
        requireNotNull(SkillManager.getSkill(id))
        return SkillManager.getSkill(id)!!
    }

    fun onEffect(
        user: BattleUnit,
        targets: List<BattleUnit>,
        messageSubject: PublishSubject<String>?
    ) {
        targets.forEach { getSkill()?.onEffect(user, it, messageSubject) }
    }

    fun startCoolDown() {
        getSkill()?.apply {
            status?.coolDown = getCoolTime()
        }
    }

    open fun updateTime(time: Long) {
        status?.apply {
            coolDown -= time
            if (coolDown < 0) {
                coolDown = 0
            }
        }
    }

    fun getName(): String {
        return getSkill().skillData.name
    }

    fun getDescription(): String {
        return getSkill().skillData.description
    }

    fun getCastingTime(): Long {
        return getSkill().skillData.castingTime
    }

    fun getEffectTime(): Long {
        return getSkill().skillData.effectTime
    }

    fun getAfterDelay(): Long {
        return getSkill().skillData.afterDelay
    }

    fun getCoolTime(): Long {
        return getSkill().skillData.coolTime
    }

    fun getTargetType(): Skill.TargetType {
        return getSkill().skillData.targetType
    }

    fun getTargetCount(): Int {
        return getSkill().skillData.targetCount
    }

    fun getCoolDown(): Long {
        return status.coolDown
    }
}