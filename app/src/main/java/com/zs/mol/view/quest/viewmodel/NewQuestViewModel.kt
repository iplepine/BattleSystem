package com.zs.mol.view.quest.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zs.mol.model.quest.Quest
import com.zs.mol.model.quest.QuestRepository
import com.zs.mol.model.user.User
import javax.inject.Inject

class NewQuestViewModel @Inject constructor(
    private val questRepository: QuestRepository,
    private val user: User
) : ViewModel() {

    val questData = MutableLiveData<Quest>()

    fun accept() {
        questData.value?.also {
            questRepository.accept(user, it.id)
        }
    }

    fun reject() {
        questData.value?.also {
            questRepository.reject(it.id)
        }
    }
}