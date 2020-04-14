package com.zs.mol.model.quest

import com.zs.mol.model.quest.factory.QuestFactory
import java.util.concurrent.ConcurrentHashMap

object QuestManager {
    val acceptedQuests = ArrayList<Quest>()
    val requests = ConcurrentHashMap<String, Quest>()

    fun createNewRequest(type: QuestType): Quest? {
        return QuestFactory.createQuest(type)?.apply {
            requests[id] = this
        }
    }

    fun createNewRequest(): Quest? {
        val randIndex = (Math.random() * QuestType.values().size).toInt()
        return createNewRequest(QuestType.values()[randIndex])
    }

    fun accept(id: String) {
        requests[id]?.also {
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
            requests.remove(it.id)
        }
    }

    fun reject(id: String) {
        requests[id]?.apply {
            requests.remove(id)
        }
    }

    fun getQuest(id: String?): Quest? {
        return requests[id] ?: acceptedQuests.find { it.id == id }
    }
}