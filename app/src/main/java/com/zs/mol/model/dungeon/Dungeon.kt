package com.zs.mol.model.dungeon

import com.zs.mol.model.quest.detail.reward.QuestReward

open class Dungeon {
    var maxUnitCount = 1
    var rewards = ArrayList<QuestReward>()
    var scenario = DungeonScenario()
}