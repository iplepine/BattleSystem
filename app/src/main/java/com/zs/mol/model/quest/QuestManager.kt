package com.zs.mol.model.quest

object QuestManager {
    val questList = ArrayList<Quest>()

    fun createNewQuest(): Quest {
        return Quest(
            "Guard carriages",
            QuestReward(100, 100),
            QuestReward(0, -50),
            ArrayList()
        )
    }
}