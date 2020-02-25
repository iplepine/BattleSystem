package com.zs.mol.model.quest

import com.zs.mol.model.quest.factory.QuestFactory

object QuestManager {
    val acceptedQuests = ArrayList<Quest>()
    val newQuests = ArrayList<Quest>()

    fun createNewRequest(): Quest? {
        return QuestFactory.createQuest(QuestType.HIRE)?.apply {
            newQuests.add(this)
        }
    }

    fun accept(id: String) {
        newQuests.find { it.id == id }?.apply {
            acceptedQuests.add(this)
            newQuests.remove(this)
        }
    }
}