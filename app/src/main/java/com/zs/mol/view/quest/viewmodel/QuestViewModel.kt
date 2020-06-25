package com.zs.mol.view.quest.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zs.mol.di.scope.GameScope
import com.zs.mol.model.quest.Quest
import com.zs.mol.model.quest.QuestRepository
import com.zs.mol.model.quest.QuestType
import com.zs.mol.model.quest.factory.QuestFactory
import com.zs.mol.model.user.User
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject

@GameScope
class QuestViewModel @Inject constructor(
    private val user: User,
    private val questRepository: QuestRepository,
    private val questFactory: QuestFactory
) : ViewModel() {
    var selectedQuest = MutableLiveData<Quest?>()
    var listType = MutableLiveData<QuestListType>().apply { value = QuestListType.ACCEPTED }

    enum class QuestListType { NEW, ACCEPTED }

    fun getAcceptedQuests(): LiveData<ArrayList<Quest>> {
        return questRepository.acceptedQuestLiveData
    }

    fun getRequests(): LiveData<ConcurrentHashMap<String, Quest>> {
        return questRepository.newQuestLiveData
    }

    fun isQuestEmpty(): Boolean {
        return getQuests().isEmpty()
    }

    fun getQuests(): ArrayList<Quest> {
        // TODO 타입이 NEW 일 때 값 제대로 넣어야 함
        return when (listType) {
            QuestListType.ACCEPTED -> questRepository.acceptedQuests
            QuestListType.NEW -> questRepository.acceptedQuests    // 이거 임시값
            else -> ArrayList()
        }
    }

    fun getQuest(id: String): Quest? {
        return getQuests().find { it.id == id }
    }

    fun getQuest(index: Int): Quest? {
        return getQuests().getOrNull(index)
    }

    fun acceptQuest() {
        val id = selectedQuest?.value?.id ?: return
        questRepository.accept(user, id)
        unSelectQuest()
    }

    fun rejectQuest() {
        val id = selectedQuest?.value?.id ?: return
        questRepository.reject(id)
        unSelectQuest()
    }

    fun refresh() {
        questRepository.clearNewQuests()
        questFactory.createQuest(QuestType.HIRE)?.also {
            questRepository.addNewRequest(it)
        }
    }

    fun selectQuest(quest: Quest?) {
        selectedQuest.value = quest
    }

    fun unSelectQuest() {
        selectedQuest.value = null
    }

    fun onClickRequest() {
        val quest = questRepository.requests.elements().nextElement()
        selectQuest(quest)
    }
}
