package com.zs.mol.model.quest

import androidx.lifecycle.MutableLiveData
import com.zs.mol.model.quest.factory.QuestFactory
import com.zs.mol.model.user.User
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QuestManager @Inject constructor(private val questFactory: QuestFactory) {
    val acceptedQuests = ArrayList<Quest>()
    val requests = ConcurrentHashMap<String, Quest>()

    val acceptedQuestLiveData = MutableLiveData<ArrayList<Quest>>()
    val newQuestLiveData = MutableLiveData<ConcurrentHashMap<String, Quest>>()

    fun createNewRequest(type: QuestType): Quest? {
        return questFactory.createQuest(type)?.apply {
            requests[id] = this
            newQuestLiveData.value = requests
        }
    }

    fun createNewRequest(): Quest? {
        val randIndex = (Math.random() * QuestType.values().size).toInt()
        return createNewRequest(QuestType.values()[randIndex])
    }

    fun accept(user: User, id: String) {
        requests[id]?.also {
            when (it.type) {
                QuestType.HIRE -> {
                    if (it.checkSuccess(user)) {
                        it.onSuccess(user)
                    } else {
                        it.onFailed(user)
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
        newQuestLiveData.value = requests
    }
}