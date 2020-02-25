package com.zs.mol.view.quest.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zs.mol.model.quest.Quest
import com.zs.mol.model.quest.QuestManager
import io.reactivex.Single
import java.util.*

class QuestViewModel : ViewModel() {

    val acceptQuests = MutableLiveData<ArrayList<Quest>>()
    val newQuests = MutableLiveData<ArrayList<Quest>>()

    fun getAcceptedQuests(): ArrayList<Quest> {
        return QuestManager.acceptedQuests
    }

    fun acceptQuest(id: String) {
        QuestManager.accept(id)
        acceptQuests.value = QuestManager.acceptedQuests
    }

    fun onClickEventButton(): Single<Quest> {
        return Single.just(QuestManager.createNewRequest())
    }
}