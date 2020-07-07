package com.zs.mol.model.quest

import androidx.lifecycle.MutableLiveData
import com.zs.mol.di.scope.GameScope
import com.zs.mol.model.quest.factory.QuestFactory
import com.zs.mol.model.user.User
import io.reactivex.Single
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject

@GameScope
class QuestRepository @Inject constructor(private val questFactory: QuestFactory) {
    val acceptedQuests = ArrayList<Quest>()
    val requests = ConcurrentHashMap<String, Quest>()

    val acceptedQuestLiveData = MutableLiveData<ArrayList<Quest>>()
    val newQuestLiveData = MutableLiveData<ConcurrentHashMap<String, Quest>>()

    fun getQuest(id: String): Quest? {
        return requests[id] ?: acceptedQuests.find { it.id == id }
    }

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

    fun clearNewQuests() {
        requests.clear()
        newQuestLiveData.value = requests
    }

    fun accept(user: User, id: String): Single<Boolean> {
        acceptedQuestLiveData.postValue(acceptedQuests)
        newQuestLiveData.postValue(requests)

        val quest = requests[id]
        if (quest == null) {
            throw Throwable("quest not found")
        } else {
            requests.remove(quest.id)

            return when (quest.type) {
                QuestType.HIRE -> {
                    Single.defer {
                        if (quest.checkSuccess(user)) {
                            quest.onSuccess(user)
                        } else {
                            quest.onFailed(user)
                        }
                    }
                }
                else -> {
                    acceptedQuests.add(quest)
                    return Single.just(true)
                }
            }
        }
    }

    fun reject(id: String): Single<Boolean> {
        return Single.create<Boolean> {
            requests.remove(id)
            newQuestLiveData.postValue(requests)
            it.onSuccess(true)
        }
    }
}