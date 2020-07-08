package com.zs.mol.model.user

import androidx.lifecycle.LiveData
import com.zs.mol.model.common.DefaultLiveData
import com.zs.mol.model.db.item.Item
import com.zs.mol.model.db.item.ItemRepository
import com.zs.mol.model.quest.Quest
import com.zs.mol.model.quest.QuestRepository
import com.zs.mol.model.unit.BattleUnit
import io.reactivex.Single

class User constructor(
    val id: String,
    var userData: UserData,
    private val itemRepository: ItemRepository,
    private val questRepository: QuestRepository
) {

    fun getUnits(): List<BattleUnit> {
        return userData.units
    }

    fun isMyUnit(id: String): Boolean {
        return id === id
    }

    fun getItemLiveData(itemId: String): LiveData<Item?> {
        return itemRepository.getItemLiveData(id, itemId)
    }

    fun getItemAmount(itemId: String): Long {
        return itemRepository.getAmount(id, itemId)
    }

    fun addItem(itemId: String, amount: Long): Single<Boolean> {
        return itemRepository.addItem(id, itemId, amount)
    }

    fun useItem(itemId: String, amount: Long): Single<Boolean> {
        return itemRepository.removeItem(id, itemId, amount)
    }

    fun getUnit(id: String): BattleUnit? {
        return userData.units.find { id == it.id }
    }

    fun addUnit(unit: BattleUnit): Single<Boolean> {
        userData.units.add(unit)
        return Single.just(true)
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
