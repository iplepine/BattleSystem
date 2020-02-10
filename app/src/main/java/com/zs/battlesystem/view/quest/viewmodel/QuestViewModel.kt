package com.zs.battlesystem.view.quest.viewmodel

import androidx.lifecycle.ViewModel
import com.zs.battlesystem.model.quest.Quest
import com.zs.battlesystem.model.quest.QuestManager

class QuestViewModel : ViewModel() {
    fun getQuests(): ArrayList<Quest> {
        return QuestManager.questList
    }

    fun acceptQuest(quest: Quest) {
    }
}