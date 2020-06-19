package com.zs.mol.view.quest.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zs.mol.model.quest.Quest
import com.zs.mol.model.quest.QuestManager
import com.zs.mol.model.user.UserManager
import javax.inject.Inject

class NewQuestViewModel @Inject constructor(
    private val questManager: QuestManager,
    private val userManager: UserManager
) : ViewModel() {

    val questData = MutableLiveData<Quest>()

    fun accept() {
        questData.value?.also {
            questManager.accept(userManager.user, it.id)
        }
    }

    fun reject() {
        questData.value?.also {
            questManager.reject(it.id)
        }
    }
}