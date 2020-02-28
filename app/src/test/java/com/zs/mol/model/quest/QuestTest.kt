package com.zs.mol.model.quest

import com.zs.mol.model.db.inventory.Inventory
import com.zs.mol.model.unit.BattleUnitFactory
import com.zs.mol.model.user.UserManager
import org.junit.Test

class QuestTest {
    @Test
    fun questBuilderTest() {
        val quest = Quest.Builder(HireQuest::class.java)
            .setTitle("새로운 지원자가 있습니다")
            .create()

        println(quest.toString())
    }

    @Test
    fun hireQuestAcceptSuccessTest() {
        val unit = BattleUnitFactory.createMyUnit()

        QuestManager.createNewRequest(QuestType.HIRE)?.apply {
            requires.clear()
            requires.add(QuestReward(RewardKey.GOLD, 500L))
            rewards.clear()
            rewards.add(QuestReward(RewardKey.UNIT, unit))

            Inventory.addItem(RewardKey.GOLD, 1000)

            QuestManager.accept(id)
            assert(UserManager.user.getUnit(unit.id) != null)
            assert(Inventory.getAmount(RewardKey.GOLD) == 500L)
        }
    }

    @Test
    fun hireQuestAcceptFailedTest() {
        val unit = BattleUnitFactory.createMyUnit()

        QuestManager.createNewRequest(QuestType.HIRE)?.apply {
            requires.clear()
            requires.add(QuestReward(RewardKey.GOLD, 500L))
            rewards.clear()
            rewards.add(QuestReward(RewardKey.UNIT, unit))

            QuestManager.accept(id)
            assert(UserManager.user.getUnit(unit.id) == null)
        }
    }
}