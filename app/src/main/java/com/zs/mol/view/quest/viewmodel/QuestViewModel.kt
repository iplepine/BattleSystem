package com.zs.mol.view.quest.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zs.mol.di.scope.GameScope
import com.zs.mol.model.quest.Quest
import com.zs.mol.model.quest.QuestRepository
import com.zs.mol.model.quest.QuestType
import com.zs.mol.model.user.User
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject

class QuestViewModel @Inject constructor(
    @GameScope
    private val user: User,
    private val questRepository: QuestRepository
) : ViewModel() {
    var selectedQuest = MutableLiveData<Quest?>()
    var listType = MutableLiveData<QuestListType>().apply { value = QuestListType.ACCEPTED }

    enum class QuestListType { NEW, ACCEPTED }

    fun getAcceptedQuests(): LiveData<ArrayList<Quest>> {
        return questRepository.acceptedQuestLiveData
    }

    fun getRequests(): LiveData<ConcurrentHashMap<String, Quest>> {
        return questRepository.newQuestLiveData
    }

    fun isQuestEmpty(): Boolean {
        return getQuests().isEmpty()
    }

    fun getQuests(): ArrayList<Quest> {
        // TODO 타입이 NEW 일 때 값 제대로 넣어야 함
        return when (listType.value) {
            QuestListType.ACCEPTED -> questRepository.acceptedQuests
            QuestListType.NEW -> questRepository.acceptedQuests    // 이거 임시값
            else -> ArrayList()
        }
    }

    fun getQuest(id: String): Quest? {
        return getQuests().find { it.id == id }
    }

    fun getQuest(index: Int): Quest? {
        return getQuests().getOrNull(index)
    }

    fun acceptQuest(): Single<Boolean> {
        val id = selectedQuest?.value?.id
        requireNotNull(id)
        return questRepository.accept(user, id)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess {
                unSelectQuest()
            }
    }

    fun rejectQuest() {
        val id = selectedQuest?.value?.id ?: return
        questRepository.reject(id)
        unSelectQuest()
    }

    fun refresh() {
        questRepository.clearNewQuests()
        questRepository.createNewRequest(QuestType.HIRE)
    }

    fun selectQuest(quest: Quest?) {
        selectedQuest.value = quest
    }

    fun unSelectQuest() {
        selectedQuest.value = null
    }
}
