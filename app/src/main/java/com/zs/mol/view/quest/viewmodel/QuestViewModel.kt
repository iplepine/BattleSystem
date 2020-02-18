package com.zs.mol.view.quest.viewmodel

import androidx.lifecycle.ViewModel
import com.zs.mol.model.quest.Quest
import com.zs.mol.model.quest.QuestManager

class QuestViewModel : ViewModel() {
    fun getQuests(): ArrayList<Quest> {
        return QuestManager.questList
    }

    fun acceptQuest(quest: Quest) {
    }
}