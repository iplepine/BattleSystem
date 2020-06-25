package com.zs.mol.model.quest

import androidx.lifecycle.MutableLiveData
import com.zs.mol.di.scope.GameScope
import com.zs.mol.model.user.User
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject

@GameScope
class QuestRepository @Inject constructor() {
    val acceptedQuests = ArrayList<Quest>()
    val requests = ConcurrentHashMap<String, Quest>()

    val acceptedQuestLiveData = MutableLiveData<ArrayList<Quest>>()
    val newQuestLiveData = MutableLiveData<ConcurrentHashMap<String, Quest>>()

    fun getQuest(id: String): Quest? {
        return requests[id] ?: acceptedQuests.find { it.id == id }
    }

    fun addNewRequest(quest: Quest) {
        requests[quest.id] = quest
        newQuestLiveData.value = requests
    }

    fun clearNewQuests() {
        requests.clear()
        newQuestLiveData.value = requests
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
}