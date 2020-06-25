package com.zs.mol.model.quest.factory

import com.zs.mol.di.scope.GameScope
import com.zs.mol.model.quest.HireQuest
import com.zs.mol.model.quest.Quest
import com.zs.mol.model.quest.QuestType
import com.zs.mol.model.quest.detail.condition.GoldRequirement
import com.zs.mol.model.quest.detail.reward.UnitReward
import com.zs.mol.model.unit.BattleUnitFactory
import com.zs.mol.model.unit.UnitRepository
import javax.inject.Inject

@GameScope
class QuestFactory @Inject constructor(
    private val battleUnitFactory: BattleUnitFactory,
    private val unitRepository: UnitRepository
) {

    fun createQuest(type: QuestType): Quest? {
        when (type) {
            QuestType.HIRE -> return createHireQuest()
            //QuestType.ITEM -> return createItemQuest()
        }

        return null
    }

    private fun createHireQuest(): HireQuest {
        val unit = battleUnitFactory.createUnit("npc")
        unitRepository.addUnit(unit)

        return Quest.Builder(HireQuest::class.java)
            .setTitle("새로운 인물")
            .setDescription("${unit.getName()}이 당신과 함께 일하고 싶어합니다.")
            .addRequire(GoldRequirement(300L))
            .addReward(UnitReward(unit))
            .setDueTime(30)
            .create()
    }
}