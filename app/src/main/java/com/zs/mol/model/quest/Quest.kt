package com.zs.mol.model.quest

import com.zs.mol.model.quest.detail.condition.QuestRequirement
import com.zs.mol.model.quest.detail.penalty.QuestPenalty
import com.zs.mol.model.quest.detail.reward.QuestReward
import com.zs.mol.model.quest.detail.reward.UnitReward
import java.util.*
import kotlin.collections.ArrayList

abstract class Quest(var type: QuestType) {
    var title: String = ""
    var description: String = ""
    var dueTime: Long = 0
    var rewards: ArrayList<QuestReward> = ArrayList()
    var penalty: ArrayList<QuestPenalty> = ArrayList()
    var requires: ArrayList<QuestRequirement> = ArrayList()
    var id: String = UUID.randomUUID().toString()

    open fun isValid(): Boolean {
        return true
    }

    open fun checkSuccess(): Boolean {
        return requires.find { !it.checkRequire() } == null
    }

    open fun onSuccess() {
        requires.forEach {
            it.onSuccess()
        }
        rewards.forEach {
            it.onSuccess()
        }
    }

    open fun onFailed() {
        penalty.forEach {
            it.onFailed()
        }
    }

    class Builder<T : Quest>(private val clazz: Class<T>) {
        private var type: QuestType =
            QuestType.REQUEST
        private var title: String = ""
        private var description: String = ""
        private var dueTime: Long = 0
        private var rewards = ArrayList<QuestReward>()
        private var penalty = ArrayList<QuestPenalty>()
        private var require = ArrayList<QuestRequirement>()

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

        fun addReward(reward: QuestReward): Builder<T> {
            this.rewards.add(reward)
            return this
        }

        fun addPenalty(penalty: QuestPenalty): Builder<T> {
            this.penalty.add(penalty)
            return this
        }

        fun addRequire(requirement: QuestRequirement): Builder<T> {
            this.require.add(requirement)
            return this
        }
    }
}