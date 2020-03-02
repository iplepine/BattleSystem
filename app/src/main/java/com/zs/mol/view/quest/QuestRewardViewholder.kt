package com.zs.mol.view.quest

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.zs.mol.R
import com.zs.mol.model.quest.QuestReward
import com.zs.mol.model.item.ItemKey
import com.zs.mol.model.unit.BattleUnit

class QuestRewardViewholder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val rewardInfo = itemView.findViewById<TextView>(R.id.questRewardInfo)

    fun bind(reward: QuestReward) {
        rewardInfo.text = when(reward.key) {
            ItemKey.UNIT ->
                "${reward.key} : ${(reward.value as BattleUnit).toSimpleInfo()}"
            else ->
                "${reward.key} : ${reward.value}"
        }
    }
}