package com.zs.mol.view.quest.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zs.mol.model.quest.Quest
import com.zs.mol.model.quest.QuestManager
import com.zs.mol.model.quest.QuestType
import io.reactivex.Completable

class QuestViewModel : ViewModel() {

    val acceptQuests = MutableLiveData<ArrayList<Quest>>().apply { value = ArrayList() }
    val newQuests = MutableLiveData<ArrayList<Quest>>().apply { value = ArrayList() }

    var selectedQuest = MutableLiveData<Quest?>()

    var listType = MutableLiveData<QuestListType>().apply { value = QuestListType.ACCEPTED }

    enum class QuestListType { NEW, ACCEPTED }

    fun isQuestEmpty(): Boolean {
        return getQuests().isEmpty()
    }

    fun getQuests(): ArrayList<Quest> {
        return when (listType) {
            QuestListType.ACCEPTED -> acceptQuests.value!!
            QuestListType.NEW -> newQuests.value!!
            else -> ArrayList()
        }
    }

    fun getQuest(index: Int): Quest? {
        return getQuests().getOrNull(index)
    }

    fun acceptQuest(id: String) {
        QuestManager.accept(id)
        acceptQuests.value = QuestManager.acceptedQuests
    }

    fun onClickEventButton(): Completable {
        val quest = QuestManager.createNewRequest(QuestType.HIRE)
        if (quest == null) {
        } else {
            selectedQuest.value = quest
        }

        return Completable.complete()
    }

    fun onClickQuestItem(quest: Quest) {
        selectedQuest.value = quest
    }
}
