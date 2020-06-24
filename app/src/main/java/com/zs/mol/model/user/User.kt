package com.zs.mol.model.user

import com.zs.mol.model.db.item.ItemRepository
import com.zs.mol.model.db.user.UserRepository
import com.zs.mol.model.quest.Quest
import com.zs.mol.model.quest.QuestRepository
import com.zs.mol.model.unit.BattleUnit
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class User @Inject constructor(
    val id: String,
    private val userRepository: UserRepository,
    private val itemRepository: ItemRepository,
    private val questRepository: QuestRepository
) {
    @Volatile
    var userStatus = UserStatus(1, 0)
    val units = ArrayList<BattleUnit>()

    val updateSubject = PublishSubject.create<Boolean>()

    fun initUser() {
        itemRepository.setItem(id, "gold", 5000)
    }

    fun getUnits(): List<BattleUnit> {
        return units
    }

    fun isMyUnit(id: String): Boolean {
        return id === id
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
        return units.find { id == it.id }
    }

    fun addUnit(unit: BattleUnit) {
        units.add(unit)
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
