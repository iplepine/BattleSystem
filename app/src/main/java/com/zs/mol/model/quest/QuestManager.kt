package com.zs.mol.model.quest

import androidx.lifecycle.MutableLiveData
import com.zs.mol.model.quest.factory.QuestFactory
import java.util.concurrent.ConcurrentHashMap

object QuestManager {
    val acceptedQuests = ArrayList<Quest>()
    val requests = ConcurrentHashMap<String, Quest>()

    val acceptedQuestLiveData = MutableLiveData<ArrayList<Quest>>()
    val newQuestLiveData = MutableLiveData<ConcurrentHashMap<String, Quest>>()

    fun createNewRequest(type: QuestType): Quest? {
        return QuestFactory.createQuest(type)?.apply {
            requests[id] = this
            newQuestLiveData.value = requests
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
                else -> {
                    acceptedQuests.add(it)
                }
            }
            requests.remove(it.id)
        }

        acceptedQuestLiveData.value = acceptedQuests
        newQuestLiveData.value = requests
    }

    fun reject(id: String) {
        requests[id]?.apply {
            requests.remove(id)
        }

        newQuestLiveData.value = requests
    }

    fun getQuest(id: String?): Quest? {
        return requests[id] ?: acceptedQuests.find { it.id == id }
    }

    fun clearNewQuests() {
        requests.clear()
        newQuestLiveData.value= requests
    }
}