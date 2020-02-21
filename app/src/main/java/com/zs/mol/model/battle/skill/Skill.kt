package com.zs.mol.model.battle.skill

import com.zs.mol.model.battle.BattleFunction
import com.zs.mol.model.battle.unit.BattleUnit
import com.zs.mol.model.db.SkillDB
import com.zs.mol.model.pojo.SkillData
import io.reactivex.subjects.PublishSubject

open abstract class Skill(val id: Int) {

    val skillData: SkillData = SkillDB.getSkillData(id)

    enum class TargetType {
        NON_TARGET, SELF, ENEMY, ALLIES, RANDOM
    }

    abstract fun onEffect(
        user: BattleUnit,
        target: BattleUnit,
        messageSubject: PublishSubject<String>?
    )

    open fun calculateDamage(unit: BattleUnit): Double {
        return BattleFunction.getDefaultAttackDamage(unit)
    }

    open fun getExpectEffect(): Int {
        return 0
    }
}