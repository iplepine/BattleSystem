package com.zs.mol.view.quest

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zs.mol.R
import com.zs.mol.model.quest.detail.QuestDetailItem

class QuestRewardAdapter(private val items: ArrayList<out QuestDetailItem>) :
    RecyclerView.Adapter<QuestDetailViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestDetailViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_quest_reward, parent, false)
        return QuestDetailViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: QuestDetailViewHolder, position: Int) {
        if (position < items.size) {
            holder.bind(items[position])
        }
    }
}