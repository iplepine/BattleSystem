package com.zs.mol.model.unit

import com.zs.mol.model.quest.detail.reward.QuestReward

class Report private constructor(
    val id: String,
    val type: ResultType,
    val message: String,
    val rewards: ArrayList<QuestReward>
) {

    enum class ResultType {
        ITEM, WIN, LOSE, DIE
    }

    companion object {
        fun newReport(
            unit: BattleUnit,
            type: ResultType,
            message: String,
            rewards: ArrayList<QuestReward>
        ): Report {
            val id = unit.id + System.currentTimeMillis()
            return Report(id, type, message, rewards)
        }
    }

    override fun toString(): String {
        return "Result : $type\n" +
                "$message"
    }
}