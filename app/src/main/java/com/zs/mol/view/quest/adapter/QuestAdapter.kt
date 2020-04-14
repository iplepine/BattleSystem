package com.zs.mol.view.quest.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zs.mol.view.quest.viewholder.QuestItemViewHolder
import com.zs.mol.view.quest.viewmodel.QuestViewModel

class QuestAdapter(private val viewModel: QuestViewModel) :
    RecyclerView.Adapter<QuestItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestItemViewHolder {
        return QuestItemViewHolder(parent, viewModel)
    }

    override fun getItemCount(): Int {
        return viewModel.getQuests().size
    }

    override fun onBindViewHolder(holder: QuestItemViewHolder, position: Int) {
        holder.bind(viewModel.getQuest(position)!!)
    }
}