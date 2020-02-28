package com.zs.mol.model.quest

import com.zs.mol.model.quest.factory.QuestFactory
import java.util.concurrent.ConcurrentHashMap

object QuestManager {
    val acceptedQuests = ArrayList<Quest>()
    val newQuests = ConcurrentHashMap<String, Quest>()

    fun createNewRequest(type: QuestType): Quest? {
        return QuestFactory.createQuest(type)?.apply {
            newQuests[id] = this
        }
    }

    fun createNewRequest(): Quest? {
        val randIndex = (Math.random() * QuestType.values().size).toInt()
        return createNewRequest(QuestType.values()[randIndex])
    }

    fun accept(id: String) {
        newQuests[id]?.also {
            when (it.type) {
                QuestType.HIRE -> {
                    if (it.checkSuccess()) {
                        it.onSuccess()
                    } else {
                        it.onFailed()
                    }
                }
                else -> acceptedQuests.add(it)
            }
            newQuests.remove(it.id)
        }
    }

    fun reject(id: String) {
        newQuests[id]?.apply {
            newQuests.remove(id)
        }
    }

    fun getQuest(id: String?): Quest? {
        return newQuests[id] ?: acceptedQuests.find { it.id == id }
    }
}