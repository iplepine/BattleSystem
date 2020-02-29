package com.zs.mol.view.quest.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zs.mol.model.quest.Quest
import com.zs.mol.model.quest.QuestManager

class NewQuestViewModel : ViewModel() {

    val questData = MutableLiveData<Quest>()

    fun accept() {
        questData.value?.also {
            QuestManager.accept(it.id)
        }
    }

    fun reject() {
        questData.value?.also {
            QuestManager.reject(it.id)
        }
    }
}