package com.zs.mol.model.quest

import com.zs.mol.model.db.inventory.Inventory
import com.zs.mol.model.item.ItemKey
import com.zs.mol.model.user.UserManager
import java.util.*

abstract class Quest(var type: QuestType) {
    var title: String = ""
    var description: String = ""
    var dueTime: Long = 0
    var rewards: ArrayList<QuestReward> = ArrayList()
    var penalty: ArrayList<QuestReward> = ArrayList()
    var requires: ArrayList<QuestReward> = ArrayList()
    var id: String = UUID.randomUUID().toString()

    open fun checkSuccess(): Boolean {
        return requires.find { !requireEnough(it) } == null
    }

    private fun requireEnough(questReward: QuestReward): Boolean {
        return when (questReward.key) {
            else -> questReward.value as? Long ?: 0 <= Inventory.getAmount(questReward.key)
        }
        return true
    }

    open fun onSuccess() {
        requires.forEach {
            when (it.key) {
                else -> Inventory.removeItem(it.key, it.value as? Long ?: 0)
            }
        }
        rewards.forEach {
            when (it.key) {
                ItemKey.EXP -> UserManager.addExp(it.value as? Long ?: 0)
                else -> Inventory.addItem(it.key, it.value as? Long ?: 0)
            }
        }
    }

    open fun onFailed() {
        penalty.forEach {
            when (it.key) {
                ItemKey.EXP -> UserManager.addExp(it.value as? Long ?: 0)
                else -> Inventory.removeItem(it.key, it.value as? Long ?: 0)
            }
        }
    }

    class Builder<T : Quest>(private val clazz: Class<T>) {
        private var type: QuestType =
            QuestType.REQUEST
        private var title: String = ""
        private var description: String = ""
        private var dueTime: Long = 0
        private var rewards = ArrayList<QuestReward>()
        private var penalty = ArrayList<QuestReward>()
        private var require = ArrayList<QuestReward>()

        fun create(): T {
            return (clazz.newInstance() as T).also {
                it.title = title
                it.description = description
                it.dueTime = dueTime
                it.rewards = rewards
                it.penalty = penalty
                it.requires = require
            }
        }

        fun setTitle(title: String): Builder<T> {
            this.title = title
            return this
        }

        fun setDescription(description: String): Builder<T> {
            this.description = description
            return this
        }

        fun setDueTime(time: Long): Builder<T> {
            this.dueTime = time
            return this
        }

        fun addReward(key: String, value: Any): Builder<T> {
            this.rewards.add(QuestReward(key, value))
            return this
        }

        fun addPenalty(key: String, value: Any): Builder<T> {
            this.penalty.add(QuestReward(key, value))
            return this
        }

        fun addRequire(key: String, value: Any): Builder<T> {
            this.require.add(QuestReward(key, value))
            return this
        }
    }
}