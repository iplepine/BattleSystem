package com.zs.mol.model.skill

import com.zs.mol.model.unit.BattleUnit
import com.zs.mol.model.manager.SkillManager
import io.reactivex.subjects.PublishSubject

class UnitSkill {

    val skill: Skill
    val status: SkillStatus

    constructor(id: Int, status: SkillStatus = SkillStatus()) {
        this.status = status
        this.skill = SkillManager.getSkill(id)!!
    }

    constructor(skill: Skill, status: SkillStatus = SkillStatus()) {
        this.status = status
        this.skill = skill
    }

    fun onEffect(
        user: BattleUnit,
        targets: List<BattleUnit>,
        messageSubject: PublishSubject<String>?
    ) {
        targets.forEach { skill?.onEffect(user, it, messageSubject) }
    }

    fun startCoolDown() {
        skill?.apply {
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
        return skill.skillData.name
    }

    fun getDescription(): String {
        return skill.skillData.description
    }

    fun getCastingTime(): Long {
        return skill.skillData.castingTime
    }

    fun getEffectTime(): Long {
        return skill.skillData.effectTime
    }

    fun getAfterDelay(): Long {
        return skill.skillData.afterDelay
    }

    fun getCoolTime(): Long {
        return skill.skillData.coolTime
    }

    fun getTargetType(): Skill.TargetType {
        return skill.skillData.targetType
    }

    fun getTargetCount(): Int {
        return skill.skillData.targetCount
    }

    fun getCoolDown(): Long {
        return status.coolDown
    }
}