package com.zs.mol.view.quest.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.zs.mol.R
import com.zs.mol.model.quest.Quest
import com.zs.mol.view.quest.viewmodel.QuestViewModel

class QuestItemViewHolder(parent: ViewGroup, private val viewModel: QuestViewModel) :
    RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.item_quest, parent, false
        )
    ) {
    private var quest: Quest? = null

    private val title = itemView.findViewById<TextView>(R.id.questTitle)
    private val description = itemView.findViewById<TextView>(R.id.questDescription)
    private val dueTime = itemView.findViewById<TextView>(R.id.dueTime)

    init {
        itemView.setOnClickListener { quest?.also { viewModel.selectQuest(it) } }
    }

    fun bind(quest: Quest) {
        this.quest = quest
        updateView()
    }

    private fun updateView() {
        quest?.also {
            title.text = it.title
            dueTime.text = "남은 시간 : ${it.dueTime}"
            description.text = it.description
        }
    }
}