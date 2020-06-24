package com.zs.mol.model.quest

import com.zs.mol.di.component.DaggerAppComponent
import com.zs.mol.di.component.GameComponent
import com.zs.mol.model.quest.detail.condition.GoldRequirement
import com.zs.mol.model.quest.detail.reward.UnitReward
import com.zs.mol.model.unit.BattleUnitFactory
import org.junit.Before
import org.junit.Test
import javax.inject.Inject

class QuestTest {

    lateinit var component: GameComponent

    @Before
    fun before() {

    }

    @Inject
    lateinit var battleUnitFactory: BattleUnitFactory

    @Test
    fun questBuilderTest() {
        val quest = Quest.Builder(HireQuest::class.java)
            .setTitle("새로운 지원자가 있습니다")
            .create()

        println(quest.toString())
    }

    @Test
    fun hireQuestAcceptSuccessTest() {
        component = DaggerAppComponent.factory().create(app)
        component.questFactory().createQuest(QuestType.HIRE)

        val unit = component.battleUnitFactory().createUnit()

        component.questRepository().createNewRequest(QuestType.HIRE)?.apply {
            requires.clear()
            requires.add(GoldRequirement(500L))
            rewards.clear()
            rewards.add(UnitReward(unit))

            UserManager.gainGold(1000)

            QuestManager.accept(id)
            assert(UserManager.user.getUnit(unit.id) != null)
            assert(UserManager.user.userStatus.gold == 500L)
        }
    }

    @Test
    fun hireQuestAcceptFailedTest() {
        val unit = BattleUnitFactory.createMyUnit()

        QuestManager.createNewRequest(QuestType.HIRE)?.apply {
            requires.clear()
            requires.add(GoldRequirement(500L))
            rewards.clear()
            rewards.add(UnitReward(unit))

            QuestManager.accept(id)
            assert(UserManager.user.getUnit(unit.id) == null)
        }
    }
}