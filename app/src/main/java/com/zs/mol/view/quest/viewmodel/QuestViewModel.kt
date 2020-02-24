package com.zs.mol.view.quest.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zs.mol.model.quest.QuestManager
import com.zs.mol.model.quest.Quest
import com.zs.mol.model.quest.HireQuest
import java.util.*

class QuestViewModel : ViewModel() {
    val newQuest = MutableLiveData<Quest>()

    fun getQuests(): ArrayList<Quest> {
        return QuestManager.questList
    }

    fun acceptQuest(quest: Quest) {
    }

    fun onClickEventButton() {
        newQuest.value = Quest.Builder(HireQuest::class.java)
            .setTitle("나는 일하고 싶다")
            .setDescription("지나가던 나그네가 우리 협회에 고용되고 싶어합니다.")
            .create()
    }
}