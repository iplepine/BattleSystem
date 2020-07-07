package com.zs.mol.view.quest.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zs.mol.model.quest.Quest
import com.zs.mol.model.quest.QuestRepository
import com.zs.mol.model.user.User
import io.reactivex.Single
import javax.inject.Inject

class NewQuestViewModel @Inject constructor(
    private val questRepository: QuestRepository,
    private val user: User
) : ViewModel() {

    val questData = MutableLiveData<Quest>()

    fun accept(): Single<Boolean> {
        val quest = questData.value
        requireNotNull(quest)
        return questRepository.accept(user, quest.id)
    }

    fun reject(): Single<Boolean> {
        val quest = questData.value
        requireNotNull(quest)
        return questRepository.reject(quest.id)
    }
}