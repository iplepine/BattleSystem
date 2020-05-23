package com.zs.mol.view.quest.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zs.mol.model.quest.Quest
import com.zs.mol.model.quest.QuestManager
import com.zs.mol.model.quest.QuestType
import java.util.concurrent.ConcurrentHashMap

class QuestViewModel : ViewModel() {
    var selectedQuest = MutableLiveData<Quest?>()
    var listType = MutableLiveData<QuestListType>().apply { value = QuestListType.ACCEPTED }

    enum class QuestListType { NEW, ACCEPTED }

    fun getAcceptedQuests(): LiveData<ArrayList<Quest>> {
        return QuestManager.acceptedQuestLiveData
    }

    fun getRequests(): LiveData<ConcurrentHashMap<String, Quest>> {
        return QuestManager.newQuestLiveData
    }

    fun isQuestEmpty(): Boolean {
        return getQuests().isEmpty()
    }

    fun getQuests(): ArrayList<Quest> {
        // TODO 타입이 NEW 일 때 값 제대로 넣어야 함
        return when (listType) {
            QuestListType.ACCEPTED -> QuestManager.acceptedQuests
            QuestListType.NEW -> QuestManager.acceptedQuests    // 이거 임시값
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
        QuestManager.accept(id)
        unSelectQuest()
    }

    fun rejectQuest() {
        val id = selectedQuest?.value?.id ?: return
        QuestManager.reject(id)
        unSelectQuest()
    }

    fun refresh() {
        QuestManager.clearNewQuests()
        QuestManager.createNewRequest(QuestType.HIRE)
    }

    fun selectQuest(quest: Quest?) {
        selectedQuest.value = quest
    }

    fun unSelectQuest() {
        selectedQuest.value = null
    }
}
