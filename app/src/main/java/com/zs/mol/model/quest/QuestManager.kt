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
        newQuests.find { it.id == id }?.also {
            when(it.type){
                QuestType.HIRE -> it.onSuccess()
                else -> acceptedQuests.add(it)
            }
            newQuests.remove(it)
        }
    }

    fun reject(id: String) {
        newQuests.find {it.id == id }?.also {
            newQuests.remove(it)
        }
    }

    fun getQuest(id: String?): Quest? {
        return newQuests.find { it.id == id }?:
        acceptedQuests.find {it.id == id}
    }
}