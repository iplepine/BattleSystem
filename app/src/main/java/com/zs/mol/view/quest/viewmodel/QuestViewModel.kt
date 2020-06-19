package com.zs.mol.view.quest.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zs.mol.di.scope.ActivityScope
import com.zs.mol.model.quest.Quest
import com.zs.mol.model.quest.QuestManager
import com.zs.mol.model.quest.QuestType
import com.zs.mol.model.user.UserManager
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject

@ActivityScope
class QuestViewModel @Inject constructor(
    private val userManager: UserManager,
    private val questManager: QuestManager
) : ViewModel() {
    var selectedQuest = MutableLiveData<Quest?>()
    var listType = MutableLiveData<QuestListType>().apply { value = QuestListType.ACCEPTED }

    enum class QuestListType { NEW, ACCEPTED }

    fun getAcceptedQuests(): LiveData<ArrayList<Quest>> {
        return questManager.acceptedQuestLiveData
    }

    fun getRequests(): LiveData<ConcurrentHashMap<String, Quest>> {
        return questManager.newQuestLiveData
    }

    fun isQuestEmpty(): Boolean {
        return getQuests().isEmpty()
    }

    fun getQuests(): ArrayList<Quest> {
        // TODO 타입이 NEW 일 때 값 제대로 넣어야 함
        return when (listType) {
            QuestListType.ACCEPTED -> questManager.acceptedQuests
            QuestListType.NEW -> questManager.acceptedQuests    // 이거 임시값
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
        questManager.accept(userManager.user, id)
        unSelectQuest()
    }

    fun rejectQuest() {
        val id = selectedQuest?.value?.id ?: return
        questManager.reject(id)
        unSelectQuest()
    }

    fun refresh() {
        questManager.clearNewQuests()
        questManager.createNewRequest(QuestType.HIRE)
    }

    fun selectQuest(quest: Quest?) {
        selectedQuest.value = quest
    }

    fun unSelectQuest() {
        selectedQuest.value = null
    }
}
