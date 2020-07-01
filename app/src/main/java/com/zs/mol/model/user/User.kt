package com.zs.mol.model.user

import androidx.lifecycle.LiveData
import com.zs.mol.model.db.item.ItemRepository
import com.zs.mol.model.quest.Quest
import com.zs.mol.model.quest.QuestRepository
import com.zs.mol.model.unit.BattleUnit
import io.reactivex.subjects.PublishSubject

class User constructor(
    val id: String,
    var userData: UserData,
    private val itemRepository: ItemRepository,
    private val questRepository: QuestRepository
) {
    val updateSubject = PublishSubject.create<Boolean>()

    init {
        val k = 0
    }

    fun getUnits(): List<BattleUnit> {
        return userData.units
    }

    fun isMyUnit(id: String): Boolean {
        return id === id
    }

    fun getItemLiveData(itemId: String): LiveData<Long> {
        return itemRepository.getItemLiveData(id, itemId)
    }

    fun getItemAmount(itemId: String): Long {
        return itemRepository.getAmount(id, itemId)
    }

    fun addItem(itemId: String, amount: Long) {
        itemRepository.addItem(id, itemId, amount)
        updateSubject.onNext(true)
    }

    fun useItem(itemId: String, amount: Long) {
        itemRepository.removeItem(id, itemId, amount)
        updateSubject.onNext(true)
    }

    fun getUnit(id: String): BattleUnit? {
        return userData.units.find { id == it.id }
    }

    fun addUnit(unit: BattleUnit) {
        userData.units.add(unit)
    }

    fun acceptQuest(quest: Quest) {

    }

    fun rejectQuest(quest: Quest) {

    }

    fun toSaveData(): Map<String, Object> {
        return HashMap<String, Object>().apply {
            // TODO 이걸 해야 돼....
        }
    }
}
