package com.zs.mol.view.quest

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.zs.mol.R
import com.zs.mol.model.quest.Quest
import com.zs.mol.model.quest.QuestReward
import com.zs.mol.model.quest.RewardKey
import com.zs.mol.model.unit.BattleUnit

class QuestRewardViewholder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val rewardInfo = itemView.findViewById<TextView>(R.id.questRewardInfo)

    fun bind(reward: QuestReward) {
        rewardInfo.text = when(reward.key) {
            RewardKey.UNIT ->
                "${reward.key} : ${(reward.value as BattleUnit).toSimpleInfo()}"
            else ->
                "${reward.key} : ${reward.value}"
        }
    }
}