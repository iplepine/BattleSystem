package com.zs.mol.view.quest

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zs.mol.R
import com.zs.mol.model.quest.QuestReward

class QuestRewardAdapter(val items : ArrayList<QuestReward>) : RecyclerView.Adapter<QuestRewardViewholder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestRewardViewholder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_quest_reward, parent, false)
        return QuestRewardViewholder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: QuestRewardViewholder, position: Int) {
        if (position < items.size) {
            holder.bind(items[position])
        }
    }
}